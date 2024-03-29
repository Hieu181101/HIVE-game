package meerkat.ui;

import javax.swing.JButton;

import meerkat.Game;
import meerkat.GamePiece;
import meerkat.PieceType;
import meerkat.Player;

abstract class PieceButton extends JButton {
    Game game;
    Player player;
    
    abstract GamePiece getPiece();
    abstract PieceType getType();
}
