package meerkat.ui;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;

import meerkat.Game;
import meerkat.GamePiece;
import meerkat.PieceType;
import meerkat.Player;
import meerkat.pieces.AntPiece;

public class AntButton extends PieceButton {
    public AntButton(Player player, Game game) {
        this.player = player;
        this.game = game;

        this.setToolTipText("Ant");
        this.setVerticalTextPosition(AbstractButton.CENTER);
        switch (player) {
            case Black:
                this.setIcon(new ImageIcon(getClass().getResource("/AntButtonBlack.png")));
                this.setHorizontalTextPosition(AbstractButton.LEFT);
                break;
            case White:
                this.setIcon(new ImageIcon(getClass().getResource("/AntButtonWhite.png")));
                this.setHorizontalTextPosition(AbstractButton.RIGHT);
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    GamePiece getPiece() {
        return new AntPiece(this.player, this.game);
    }

    @Override
    PieceType getType() {
        return PieceType.Ant;
    }
}
