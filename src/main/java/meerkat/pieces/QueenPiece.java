package meerkat.pieces;

import java.util.ArrayList;
import java.util.Collection;

import meerkat.BoardPosition;
import meerkat.Game;
import meerkat.GameBoard;
import meerkat.GamePiece;
import meerkat.PieceFlyweightFactory;
import meerkat.PieceType;
import meerkat.Player;

public class QueenPiece extends GamePiece {
    public QueenPiece(Player owner, Game game) {
        this.owner = owner;
        this.flyweight = PieceFlyweightFactory.getInstance().get(PieceType.Queen);
        this.game = game;
    }

    public Collection<BoardPosition> getLegalMoves() {
        // no need to check if queen is placed
        ArrayList<BoardPosition> moves = new ArrayList<>();

        if (canMove()) {
            GameBoard board = this.game.getBoard();
            for (BoardPosition dir : BoardPosition.DIRECTIONS) {
                BoardPosition neighbour = this.pos.add(dir);
                if (!board.isOccupied(neighbour)
                        && !(board.isOccupied(this.pos.add(dir.rotateLeft()))
                                && board.isOccupied(this.pos.add(dir.rotateRight())))) {
                    for (BoardPosition adj : neighbour.getNeighbours()) {
                        if (!adj.equals(pos) && board.isOccupied(adj)) {
                            moves.add(neighbour);
                            break;
                        }
                    }
                }
            }
        }
        return moves;
    }
}
