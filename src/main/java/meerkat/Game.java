package meerkat;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;

import meerkat.pieces.AntPiece;
import meerkat.pieces.BeetlePiece;
import meerkat.pieces.GrasshopperPiece;
import meerkat.pieces.QueenPiece;
import meerkat.pieces.SpiderPiece;

public class Game {
    private GameBoard board;
    private Player activePlayer;
    private int turn;

    private Hand whiteHand;
    private Hand blackHand;

    private boolean hasAi;

    public void init(boolean hasAi) {
        this.board = new GameBoard();
        this.turn = 1;
        this.activePlayer = Player.White;
        this.whiteHand = new Hand();
        this.blackHand = new Hand();
        this.hasAi = hasAi;

    }

    public void nextPhase() {
        switch (this.activePlayer) {
            case Black:
                this.activePlayer = Player.White;
                this.turn += 1;
                break;
            case White:
                if (hasAi) {
                    aiMove();
                } else {
                    this.activePlayer = Player.Black;
                }
                break;
            default:
                throw new IllegalStateException();
        }

    }

    public GameBoard getBoard() {
        return this.board;
    }

    public int getTurn() {
        return this.turn;
    }

    public Player getActivePlayer() {
        return this.activePlayer;
    }

    public Hand getHand(Player player) {
        switch (player) {
            case Black:
                return this.blackHand;
            case White:
                return this.whiteHand;
            default:
                throw new IllegalStateException();

        }
    }

    private void aiMove() {
        boolean hasPieceMoves = false;
        boolean hasPlacements = false;

        for (Deque<GamePiece> pieceStack : board.getPieces()) {
            if (pieceStack.peek().getOwner() == Player.Black) {
                if (!pieceStack.peek().getLegalMoves().isEmpty()) {
                    hasPieceMoves = true;
                    break;
                }
            }
        }

        if (!blackHand.isEmpty() && !board.getLegalPlacements(Player.Black).isEmpty()) {
            hasPlacements = true;
        }

        boolean placePiece;
        if (hasPlacements && !hasPieceMoves) {
            placePiece = true;
        } else if (!hasPlacements && hasPieceMoves) {
            placePiece = false;
        } else if (hasPlacements && hasPieceMoves) {
            placePiece = Math.random() > 0.5;
        } else {
            // no legal moves = pass
            return;
        }

        if (placePiece) {
            ArrayList<GamePiece> availablePieces = new ArrayList<>();
            if (blackHand.ants > 0) {
                availablePieces.add(new AntPiece(Player.Black, this));
            }
            if (blackHand.beetles > 0) {
                availablePieces.add(new BeetlePiece(Player.Black, this));
            }
            if (blackHand.grasshoppers > 0) {
                availablePieces.add(new GrasshopperPiece(Player.Black, this));
            }
            if (blackHand.queens > 0) {
                availablePieces.add(new QueenPiece(Player.Black, this));
            }
            if (blackHand.spiders > 0) {
                availablePieces.add(new SpiderPiece(Player.Black, this));
            }

            int pieceIndex = (int) Math.floor(Math.random() * availablePieces.size());

            GamePiece piece = availablePieces.get(pieceIndex);

            List<BoardPosition> placements = board.getLegalPlacements(Player.Black);

            int placementIndex = (int) Math.floor(Math.random() * placements.size());

            BoardPosition pos = placements.get(placementIndex);

            board.place(piece, pos);
            blackHand.decrementPiece(piece.getType());

        } else {
            ArrayList<BoardPosition[]> legalMoves = new ArrayList<>();
            for (Deque<GamePiece> pieceStack : board.getPieces()) {
                GamePiece piece = pieceStack.peek();
                if (piece.getOwner() == Player.Black) {
                    Collection<BoardPosition> moves = piece.getLegalMoves();
                    for (BoardPosition move : moves) {
                        legalMoves.add(new BoardPosition[] { piece.pos, move });
                    }
                }
            }
            int moveIndex = (int) Math.floor(Math.random() * legalMoves.size());
            BoardPosition[] move = legalMoves.get(moveIndex);
            board.move(move[0], move[1]);
        }
    }
}
