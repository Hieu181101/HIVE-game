package meerkat.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.UIManager;

import meerkat.BoardPosition;
import meerkat.Game;
import meerkat.GamePiece;

public class PlaceState implements BoardPanelState {
    GamePiece piece;
    BoardPanel panel;

    public PlaceState(GamePiece piece, BoardPanel panel) {
        this.piece = piece;
        this.panel = panel;

        panel.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g, Game game, Point offset) {
        if (panel.offset == null) {
            this.panel.setBackground(Color.GREEN);
        } else {
            for (BoardPosition pos : game.getBoard().getLegalPlacements(piece.getOwner())) {
                this.panel.drawHex(g, pos, offset, Color.GREEN);
            }
        }
    }

    @Override
    public void cleanup() {
        this.panel.removeMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (this.panel.offset == null) {
            this.panel.offset = new Point(event.getX(), event.getY());
            this.panel.game.getBoard().place(this.piece, BoardPosition.ORIGIN);
            this.panel.game.getHand(this.piece.getOwner()).decrementPiece(this.piece.getType());
            this.panel.setBackground(UIManager.getColor("Panel.background"));

            this.panel.game.nextPhase();
        } else {
            BoardPosition pos = this.panel.boardPosFromScreenPos(event.getX(), event.getY());

            if (this.panel.game.getBoard().getLegalPlacements(this.piece.getOwner()).contains(pos)) {
                this.panel.game.getBoard().place(this.piece, pos);
                this.panel.game.getHand(this.piece.getOwner()).decrementPiece(this.piece.getType());
                this.panel.game.nextPhase();
            }
        }
        this.panel.state = new SelectionState(this.panel);
        this.cleanup();

        this.panel.updateUi();
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

}
