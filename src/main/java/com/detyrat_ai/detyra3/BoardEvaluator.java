package com.detyrat_ai.detyra3;

public class BoardEvaluator {
    private static final int PAWN = 1;
    private static final int KNIGHT = 3;
    private static final int BISHOP = 3;
    private static final int ROOK = 5;
    private static final int QUEEN = 9;
    private static final int KING = 100;

    public static int evaluate(GameState state) {
        int score = 0;
        char[][] board = state.getBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                char piece = board[row][col];
                score += getPieceValue(piece);
            }
        }
        return score;
    }

    private static int getPieceValue(char piece) {
        switch (Character.toLowerCase(piece)) {
            case 'p': return PAWN * (Character.isUpperCase(piece) ? 1 : -1);
            case 'n': return KNIGHT * (Character.isUpperCase(piece) ? 1 : -1);
            case 'b': return BISHOP * (Character.isUpperCase(piece) ? 1 : -1);
            case 'r': return ROOK * (Character.isUpperCase(piece) ? 1 : -1);
            case 'q': return QUEEN * (Character.isUpperCase(piece) ? 1 : -1);
            case 'k': return KING * (Character.isUpperCase(piece) ? 1 : -1);
            default: return 0;
        }
    }
}
