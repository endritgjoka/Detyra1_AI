package com.detyrat_ai.detyra3;

// GameState.java
import java.util.List;

public class GameState {
    public char[][] board; // 2D array representing the chessboard
    private boolean isMaxTurn; // Indicates if it's MAX's turn

    public GameState() {
        this.board = new char[8][8];
        this.isMaxTurn = true;
    }

    public void initializeMidGame() {
        // Initialize board with a mid-game state using letters
        // Uppercase for MAX's pieces, lowercase for MIN's pieces
        board = new char[][]{
                { 'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r' }, // MIN's back rank
                { 'p', 'p', 'p', 'p', '.', 'p', 'p', 'p' }, // MIN's pawns (with one moved)
                { '.', '.', '.', '.', 'p', '.', '.', '.' }, // Example mid-game positioning
                { '.', '.', '.', '.', '.', '.', 'P', '.' }, // Example mid-game positioning
                { '.', '.', '.', 'P', '.', '.', '.', '.' }, // Example mid-game positioning
                { '.', '.', '.', '.', '.', '.', '.', '.' }, // Empty squares
                { 'P', 'P', 'P', '.', 'P', 'P', 'P', 'P' }, // MAX's pawns
                { 'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R' }  // MAX's back rank
        };
        isMaxTurn = true; // MAX plays first
    }

    public List<Move> generateMoves() {
        return MoveGenerator.generateLegalMoves(this);
    }

    public void applyMove(Move move) {
        board[move.toRow][move.toCol] = board[move.fromRow][move.fromCol];
        board[move.fromRow][move.fromCol] = '.';
        isMaxTurn = !isMaxTurn;
    }

    public void undoMove(Move move, char capturedPiece) {
        board[move.fromRow][move.fromCol] = board[move.toRow][move.toCol];
        board[move.toRow][move.toCol] = capturedPiece;
        isMaxTurn = !isMaxTurn;
    }

    public int evaluate() {
        // Evaluation function based on piece value and mobility
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                score += getPieceValue(board[i][j]);
            }
        }
        return score;
    }

    private int getPieceValue(char piece) {
        switch (piece) {
            case 'P': return 1;
            case 'N': case 'B': return 3;
            case 'R': return 5;
            case 'Q': return 9;
            case 'K': return 100;
            case 'p': return -1;
            case 'n': case 'b': return -3;
            case 'r': return -5;
            case 'q': return -9;
            case 'k': return -100;
            default: return 0;
        }
    }

    public boolean isMaxTurn() {
        return isMaxTurn;
    }
}
