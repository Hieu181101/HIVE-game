package meerkat.ui;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;

import meerkat.Game;
import meerkat.GamePiece;
import meerkat.PieceType;
import meerkat.Player;
import meerkat.pieces.GrasshopperPiece;

public class GrasshopperButton extends PieceButton {
    public GrasshopperButton(Player player, Game game) {
        this.player = player;
        this.game = game;

        this.setToolTipText("Grasshopper");
        this.setVerticalTextPosition(AbstractButton.CENTER);
        switch (player) {
            case Black:
                this.setIcon(new ImageIcon(getClass().getResource("/GrasshopperButtonBlack.png")));
                this.setHorizontalTextPosition(AbstractButton.LEFT);
                break;
            case White:
                this.setIcon(new ImageIcon(getClass().getResource("/GrasshopperButtonWhite.png")));
                this.setHorizontalTextPosition(AbstractButton.RIGHT);
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    GamePiece getPiece() {
        return new GrasshopperPiece(this.player, this.game);
    }

    @Override
    PieceType getType() {
        return PieceType.Grasshopper;
    }
}
