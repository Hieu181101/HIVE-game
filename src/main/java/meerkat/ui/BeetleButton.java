package meerkat.ui;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;

import meerkat.Game;
import meerkat.GamePiece;
import meerkat.PieceType;
import meerkat.Player;
import meerkat.pieces.BeetlePiece;

public class BeetleButton extends PieceButton {
    public BeetleButton(Player player, Game game) {
        this.player = player;
        this.game = game;
        this.setToolTipText("Beetle");
        this.setVerticalTextPosition(AbstractButton.CENTER);
        switch (player) {
            case Black:
                this.setIcon(new ImageIcon(getClass().getResource("/BeetleButtonBlack.png")));
                this.setHorizontalTextPosition(AbstractButton.LEFT);
                break;
            case White:
                this.setIcon(new ImageIcon(getClass().getResource("/BeetleButtonWhite.png")));
                this.setHorizontalTextPosition(AbstractButton.RIGHT);
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    GamePiece getPiece() {
        return new BeetlePiece(this.player, this.game);
    }

    @Override
    PieceType getType() {
        return PieceType.Beetle;
    }
}
