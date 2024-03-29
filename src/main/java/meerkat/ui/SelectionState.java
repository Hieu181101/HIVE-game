package meerkat.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Deque;

import meerkat.BoardPosition;
import meerkat.Game;
import meerkat.GamePiece;

public class SelectionState implements BoardPanelState {
    BoardPanel panel;

    public SelectionState(BoardPanel panel) {
        this.panel = panel;

        panel.addMouseListener(this);
    }

    @Override
    public void paint(Graphics g, Game game, Point offset) {
        Point mouse_pos = panel.getMousePosition();
        if (offset != null && mouse_pos != null) {
            BoardPosition pos = panel.boardPosFromScreenPos(mouse_pos.x, mouse_pos.y);
            Deque<GamePiece> pieces = game.getBoard().getPiecesAt(pos);
            if (pieces != null && pieces.size() > 0) {
                panel.drawHex(g, pos, offset, new Color(0, 255, 0, 100));
            }
        }
    }

    @Override
    public void cleanup() {
        this.panel.removeMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (panel.offset == null) {
            return;
        }

        Point mouse_pos = panel.getMousePosition();
        BoardPosition pos = panel.boardPosFromScreenPos(mouse_pos.x, mouse_pos.y);

        Deque<GamePiece> pieces = panel.game.getBoard().getPiecesAt(pos);

        if (pieces != null && pieces.peek().getOwner() == panel.game.getActivePlayer()) {
            this.panel.state = new MoveState(this.panel, pieces.peek());
            this.cleanup();
        }
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
