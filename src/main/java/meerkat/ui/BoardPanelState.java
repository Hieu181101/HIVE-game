package meerkat.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseListener;

import meerkat.Game;

public interface BoardPanelState extends MouseListener {
    void paint(Graphics g, Game game, Point offset);
    void cleanup();
}
