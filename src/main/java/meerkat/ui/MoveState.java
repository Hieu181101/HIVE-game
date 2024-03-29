package meerkat.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import meerkat.BoardPosition;
import meerkat.Game;
import meerkat.GamePiece;

public class MoveState implements BoardPanelState {
    BoardPanel panel;
    GamePiece piece;

    public MoveState(BoardPanel panel, GamePiece piece) {
        this.panel = panel;
        this.piece = piece;
        panel.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g, Game game, Point offset) {
        panel.drawHex(g, piece.pos, offset, new Color(255, 255, 0, 100));
        for (BoardPosition pos : this.piece.getLegalMoves()) {
            if (game.getBoard().getPiecesAt(pos) == null) {
                panel.drawHex(g, pos, offset, Color.GREEN);
            } else {
                panel.drawHex(g, pos, offset, new Color(0, 255, 0, 100));
            }
        }
    }

    @Override
    public void cleanup() {
        panel.removeMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        BoardPosition clickPos = this.panel.boardPosFromScreenPos(event.getX(), event.getY());
        boolean legal = false;
        for (BoardPosition pos : this.piece.getLegalMoves()) {
            if (pos.equals(clickPos)) {
                legal = true;
                break;
            }
        }

        if (legal) {
            this.panel.game.getBoard().move(piece.pos, clickPos);
            this.panel.game.nextPhase();
            this.panel.updateUi();
        }

        panel.state = new SelectionState(panel);
        this.cleanup();
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
