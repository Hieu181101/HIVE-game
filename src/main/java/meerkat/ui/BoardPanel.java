package meerkat.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Deque;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import meerkat.BoardPosition;
import meerkat.Game;
import meerkat.GamePiece;
import meerkat.Player;

public class BoardPanel extends JPanel implements ActionListener {

    public static final int HEX_SIZE = 60;
    public static final int GRID_GAP = 3;

    Point offset;
    Game game;
    BoardPanelState state;

    PieceButton[] whiteButtons;
    PieceButton[] blackButtons;

    public BoardPanel() {
        this.setLayout(new BorderLayout());
        MouseAdapter dragAdapter = new MouseAdapter() {
            int dragStartX;
            int dragStartY;
            Point initialOffset;

            @Override
            public void mousePressed(MouseEvent event) {
                if (offset != null) {
                    dragStartX = event.getX();
                    dragStartY = event.getY();
                    initialOffset = new Point(offset);
                }
            }

            @Override
            public void mouseDragged(MouseEvent event) {
                if (offset != null) {
                    offset.x = initialOffset.x + (event.getX() - dragStartX);
                    offset.y = initialOffset.y + (event.getY() - dragStartY);
                }
            }

        };
        this.game = new Game();

        this.addMouseMotionListener(dragAdapter);
        this.addMouseListener(dragAdapter);

        JPanel blackButtonPanel = new JPanel();
        BoxLayout blackButtonLayout = new BoxLayout(blackButtonPanel, BoxLayout.Y_AXIS);
        blackButtonPanel.setLayout(blackButtonLayout);
        JPanel whiteButtonPanel = new JPanel();
        BoxLayout whiteButtonLayout = new BoxLayout(whiteButtonPanel, BoxLayout.Y_AXIS);
        whiteButtonPanel.setLayout(whiteButtonLayout);

        this.add(blackButtonPanel, BorderLayout.WEST);
        this.add(whiteButtonPanel, BorderLayout.EAST);

        blackButtons = this.createPieceButtons(Player.Black);
        whiteButtons = this.createPieceButtons(Player.White);

        blackButtonPanel.add(Box.createVerticalGlue());
        for (PieceButton button : blackButtons) {
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setAlignmentY(Component.CENTER_ALIGNMENT);
            button.setEnabled(false);
            button.addActionListener(this);
            blackButtonPanel.add(button);
        }
        blackButtonPanel.add(Box.createVerticalGlue());

        whiteButtonPanel.add(Box.createVerticalGlue());
        for (PieceButton button : whiteButtons) {
            button.setAlignmentX(Component.RIGHT_ALIGNMENT);
            button.setAlignmentY(Component.CENTER_ALIGNMENT);
            button.addActionListener(this);
            whiteButtonPanel.add(button);
        }
        whiteButtonPanel.add(Box.createVerticalGlue());

        this.state = new SelectionState(this);
    }

    public void initGame(boolean hasAi) {
        this.game.init(hasAi);
        this.updateUi();
    }

    public void updateUi() {
        for (PieceButton button : whiteButtons) {
            int count = game.getHand(Player.White).getCount(button.getType());
            button.setText("x" + count);
            boolean enabled = game.getActivePlayer() == Player.White && count != 0;
            button.setEnabled(enabled);
        }

        for (PieceButton button : blackButtons) {
            int count = game.getHand(Player.Black).getCount(button.getType());
            button.setText(count + "x");
            boolean enabled = game.getActivePlayer() == Player.Black && count != 0;
            button.setEnabled(enabled);
        }
    }

    private PieceButton[] createPieceButtons(Player player) {
        AntButton antButton = new AntButton(player, this.game);
        BeetleButton beetleButton = new BeetleButton(player, this.game);
        GrasshopperButton grasshopperButton = new GrasshopperButton(player, this.game);
        QueenButton queenButton = new QueenButton(player, this.game);
        SpiderButton spiderButton = new SpiderButton(player, this.game);

        PieceButton[] arr = {
                antButton,
                beetleButton,
                grasshopperButton,
                queenButton,
                spiderButton,
        };
        return arr;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() instanceof PieceButton) {
            GamePiece piece = ((PieceButton) event.getSource()).getPiece();
            this.removeMouseListener(this.state);

            this.state.cleanup();
            this.state = new PlaceState(piece, this);
        }
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        for (Deque<GamePiece> pieceStack : game.getBoard().getPieces()) {
            GamePiece piece = pieceStack.peek();

            Color pieceColor;
            switch (piece.getOwner()) {
                case White:
                    // #fafaba
                    pieceColor = new Color(250, 250, 186);
                    break;
                case Black:
                    // #2a2a2a
                    pieceColor = new Color(40, 40, 40);
                    break;

                default:
                    throw new IllegalStateException();
            }

            drawHex(g, piece.pos, offset, pieceColor);
            piece.draw(g, piece.pos, offset, HEX_SIZE);
        }

        state.paint(g, this.game, this.offset);

        super.paintChildren(g);
    }

    public void drawHex(Graphics g, BoardPosition pos, Point offset, Color color) {
        Point center = new Point(
                offset.x + (int) (HEX_SIZE * 1.5 * pos.getR()),
                offset.y + (int) -(HEX_SIZE * (Math.sqrt(3) * pos.getS() + Math.sqrt(3) / 2 * pos.getR())));

        int[] xCorners = new int[6];
        int[] yCorners = new int[6];

        for (int i = 0; i < 6; i++) {
            xCorners[i] = center.x + (int) (Math.cos(i * Math.PI / 3) * (HEX_SIZE - GRID_GAP));
            yCorners[i] = center.y + (int) (Math.sin(i * Math.PI / 3) * (HEX_SIZE - GRID_GAP));
        }
        Color originalColor = g.getColor();
        g.setColor(color);
        g.fillPolygon(xCorners, yCorners, 6);
        g.setColor(originalColor);
    }

    public BoardPosition boardPosFromScreenPos(int x, int y) {
        x -= offset.x;
        y -= offset.y;

        float r = (float) ((2.0 / 3.0) * x) / HEX_SIZE;
        float s = (float) (-1.0f / 3 * x + Math.sqrt(3.0f) / 3.0f * -y) / HEX_SIZE;
        float t = -r - s;

        int r_rounded = Math.round(r);
        int s_rounded = Math.round(s);
        int t_rounded = Math.round(t);

        float r_fract = Math.abs(r_rounded - r);
        float s_fract = Math.abs(s_rounded - s);
        float t_fract = Math.abs(t_rounded - t);

        if (r_fract > s_fract && r_fract > t_fract) {
            r_rounded = -s_rounded - t_rounded;
        } else if (s_fract > t_fract) {
            s_rounded = -r_rounded - t_rounded;
        } else {
            t_rounded = -r_rounded - s_rounded;
        }

        return new BoardPosition(r_rounded, s_rounded, t_rounded);

    }
}
