package meerkat;

import java.util.List;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;

public class GameBoard {
    private Map<BoardPosition, Deque<GamePiece>> data;

    public GameBoard() {
        this.data = new HashMap<>();
    }

    public void move(BoardPosition from, BoardPosition to) {
        Deque<GamePiece> start = this.data.get(from);
        if (start != null && start.size() > 0) {
            GamePiece piece = start.pop();
            if (start.size() == 0) {
                this.data.remove(from);
            }
            piece.pos = to;
            if (this.data.get(to) == null) {
                Deque<GamePiece> item = new ArrayDeque<>();
                item.add(piece);
                this.data.put(to, item);
            } else {
                this.data.get(to).push(piece);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void place(GamePiece piece, BoardPosition to) {
        if (this.data.get(to) == null) {
            piece.pos = to;
            Deque<GamePiece> item = new ArrayDeque<>();
            item.add(piece);
            this.data.put(to, item);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<BoardPosition> getLegalPlacements(Player player) {
        ArrayList<BoardPosition> placements = new ArrayList<>();
        if (data.size() == 1) {
            Map.Entry<BoardPosition, ?> entry = data.entrySet().iterator().next();

            BoardPosition pos = entry.getKey();
            for (BoardPosition neighbour : pos.getNeighbours()) {
                placements.add(neighbour);
            }

        } else {
            for (Map.Entry<BoardPosition, Deque<GamePiece>> entry : data.entrySet()) {
                BoardPosition pos = entry.getKey();
                Deque<GamePiece> piece = entry.getValue();

                if (piece.peek().getOwner() != player) {
                    continue;
                }

                for (BoardPosition neighbour : pos.getNeighbours()) {
                    if (data.get(neighbour) != null) {
                        continue;
                    }
                    boolean valid = true;
                    for (BoardPosition adj : neighbour.getNeighbours()) {
                        if (data.get(adj) != null && data.get(adj).peek().getOwner() != player) {
                            valid = false;
                            break;
                        }
                    }
                    if (valid) {
                        placements.add(neighbour);
                    }
                }

            }
        }
        return placements;
    }

    public Deque<GamePiece> getPiecesAt(BoardPosition pos) {
        return data.get(pos);
    }

    public Iterable<Deque<GamePiece>> getPieces() {
        return data.values();
    }

    public boolean isOccupied(BoardPosition pos) {
        Deque<?> value = this.data.get(pos);
        return (value != null && value.size() > 0);
    }
}
