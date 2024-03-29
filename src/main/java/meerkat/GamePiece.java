package meerkat;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public abstract class GamePiece {
    protected Player owner;
    protected PieceFlyweight flyweight;
    protected Game game;
    public BoardPosition pos;

    public Player getOwner() {
        return this.owner;
    };

    public PieceType getType() {
        return flyweight.type;
    }

    public abstract Collection<BoardPosition> getLegalMoves();

    public void draw(Graphics g, BoardPosition pos, Point offset, int size) {
        Point center = new Point(
                offset.x + (int) (size * 1.5 * pos.getR()),
                offset.y + (int) -(size * (Math.sqrt(3) * pos.getS() + Math.sqrt(3) / 2 * pos.getR())));
        flyweight.draw(g, center, size);
    }

    protected boolean canMove() {
        ArrayList<BoardPosition> existingNeighbours = new ArrayList<>();

        for (BoardPosition neighbour : pos.getNeighbours()) {
            if (game.getBoard().isOccupied(neighbour)) {
                existingNeighbours.add(neighbour);
            }
        }

        if (existingNeighbours.size() == 0) {
            return false;
        }
        if (existingNeighbours.size() == 1) {
            return true;
        }

        HashSet<BoardPosition> visited = new HashSet<>();
        ArrayDeque<BoardPosition> toVisit = new ArrayDeque<>();

        visited.add(this.pos);
        toVisit.addLast(existingNeighbours.get(0));

        while (!toVisit.isEmpty()) {
            BoardPosition next = toVisit.pollFirst();
            visited.add(next);
            for (BoardPosition neighbour : next.getNeighbours()) {
                if (!visited.contains(neighbour) && game.getBoard().isOccupied(neighbour)) {
                    toVisit.addLast(neighbour);
                }
            }
        }

        for (BoardPosition neighbour : existingNeighbours) {
            if (!visited.contains(neighbour)) {
                return false;
            }
        }
        return true;
    }
}
