package meerkat.ui;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;

import meerkat.Game;
import meerkat.GamePiece;
import meerkat.PieceType;
import meerkat.Player;
import meerkat.pieces.QueenPiece;

public class QueenButton extends PieceButton {
    public QueenButton(Player player, Game game) {
        this.player = player;
        this.game = game;

        this.setToolTipText("Queen");
        this.setVerticalTextPosition(AbstractButton.CENTER);
        switch (player) {
            case Black:
                this.setIcon(new ImageIcon(getClass().getResource("/QueenButtonBlack.png")));
                this.setHorizontalTextPosition(AbstractButton.LEFT);
                break;
            case White:
                this.setIcon(new ImageIcon(getClass().getResource("/QueenButtonWhite.png")));
                this.setHorizontalTextPosition(AbstractButton.RIGHT);
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    GamePiece getPiece() {
        return new QueenPiece(this.player, this.game);
    }

    @Override
    PieceType getType() {
        return PieceType.Queen;
    }
}
