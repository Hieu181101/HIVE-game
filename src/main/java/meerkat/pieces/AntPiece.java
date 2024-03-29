package meerkat.pieces;

import java.util.ArrayList;
import java.util.Collection;

import meerkat.BoardPosition;
import meerkat.Game;
import meerkat.GamePiece;
import meerkat.PieceFlyweightFactory;
import meerkat.PieceType;
import meerkat.Player;

public class AntPiece extends GamePiece {
    public AntPiece(Player owner, Game game) {
        this.owner = owner;
        this.flyweight = PieceFlyweightFactory.getInstance().get(PieceType.Ant);
        this.game = game;
    }

    public Collection<BoardPosition> getLegalMoves() {
        // TODO
        return new ArrayList<BoardPosition>();
    }
}
