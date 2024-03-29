package meerkat.ui;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;

import meerkat.Game;
import meerkat.GamePiece;
import meerkat.PieceType;
import meerkat.Player;
import meerkat.pieces.SpiderPiece;

public class SpiderButton extends PieceButton {
    public SpiderButton(Player player, Game game) {
        this.player = player;
        this.game = game;

        this.setToolTipText("Spider");
        this.setVerticalTextPosition(AbstractButton.CENTER);
        switch (player) {
            case Black:
                this.setIcon(new ImageIcon(getClass().getResource("/SpiderButtonBlack.png")));
                this.setHorizontalTextPosition(AbstractButton.LEFT);
                break;
            case White:
                this.setIcon(new ImageIcon(getClass().getResource("/SpiderButtonWhite.png")));
                this.setHorizontalTextPosition(AbstractButton.RIGHT);
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    GamePiece getPiece() {
        return new SpiderPiece(this.player, this.game);
    }

    @Override
    PieceType getType() {
        return PieceType.Spider;
    }
}
