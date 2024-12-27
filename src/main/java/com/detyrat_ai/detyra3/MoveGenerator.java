package com.detyrat_ai.detyra3;

import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {
    public static List<Move> generateLegalMoves(GameState state) {
        List<Move> moves = new ArrayList<>();
        char[][] board = state.board;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((state.isMaxTurn() && Character.isUpperCase(board[i][j])) || (!state.isMaxTurn() && Character.isLowerCase(board[i][j]))) {
                    moves.addAll(generateMovesForPiece(board, i, j));
                }
            }
        }
        return moves;
    }

    private static List<Move> generateMovesForPiece(char[][] board, int row, int col) {
        List<Move> moves = new ArrayList<>();
        char piece = board[row][col];

        // Example: Generate pseudo-legal moves for a pawn
        if (piece == 'P' || piece == 'p') {
            int direction = piece == 'P' ? -1 : 1;
            if (isValidMove(board, row + direction, col)) {
                moves.add(new Move(row, col, row + direction, col));
            }
        }
        return moves;
    }

    private static boolean isValidMove(char[][] board, int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8 && board[row][col] == '.';
    }
}
