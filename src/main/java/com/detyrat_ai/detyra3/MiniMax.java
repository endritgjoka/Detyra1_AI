package com.detyrat_ai.detyra3;

import java.util.List;

public class MiniMax {
    private static final int BOARD_SIZE = 8;

    public boolean isMoveValid(GameState gameState, Move move) {
        int fromX = move.getFromX();
        int fromY = move.getFromY();
        int toX = move.getToX();
        int toY = move.getToY();

        // Check if the destination is out of bounds
        if (toX < 0 || toX >= BOARD_SIZE || toY < 0 || toY >= BOARD_SIZE) {
            return false;
        }

        // Check if the source position contains a piece
        if (gameState.getBoard()[fromX][fromY] == '.') {
            return false;
        }

        // Check if the destination is occupied by the same player's piece
        if (gameState.getBoard()[toX][toY] != '.' &&
                Character.isUpperCase(gameState.getBoard()[fromX][fromY]) == Character.isUpperCase(gameState.getBoard()[toX][toY])) {
            return false;
        }

        return true;
    }

    public int minimax(GameState gameState, int depth, boolean isMaximizingPlayer, int alpha, int beta) {
        if (depth == 0) {
            return evaluateBoard(gameState);
        }

        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            // Loop over all possible moves and evaluate
            for (Move move : getAllValidMoves(gameState, true)) {
                GameState newGameState = simulateMove(gameState, move);
                int eval = minimax(newGameState, depth - 1, false, alpha, beta);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);  // Update alpha
                if (beta <= alpha) {
                    break;  // Beta cut-off
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            // Loop over all possible moves and evaluate
            for (Move move : getAllValidMoves(gameState, false)) {
                GameState newGameState = simulateMove(gameState, move);
                int eval = minimax(newGameState, depth - 1, true, alpha, beta);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);  // Update beta
                if (beta <= alpha) {
                    break;  // Alpha cut-off
                }
            }
            return minEval;
        }
    }


    // A method to evaluate the board (customize it based on your game rules)
    private int evaluateBoard(GameState gameState) {
        int evaluation = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                char piece = gameState.getBoard()[i][j];
                if (piece != '.') {
                    evaluation += evaluatePiece(piece, i, j);
                }
            }
        }
        return evaluation;
    }

    // A method to evaluate a specific piece
    private int evaluatePiece(char piece, int x, int y) {
        int value = 0;
        switch (Character.toLowerCase(piece)) {
            case 'p':
                value = 10;
                break;
            case 'n':
                value = 30;
                break;
            case 'b':
                value = 30;
                break;
            case 'r':
                value = 50;
                break;
            case 'q':
                value = 90;
                break;
            case 'k':
                value = 900;
                break;
        }

        // Adjust value for position, e.g., pawn in the center is better than on the edge
        if (Character.isUpperCase(piece)) {
            value = -value; // Reverse the value for the opponent's pieces
        }
        return value;
    }

    // Method to get all valid moves for the current player
    List<Move> getAllValidMoves(GameState gameState, boolean isMaximizingPlayer) {
        MoveGenerator moveGenerator = new MoveGenerator();
        List<Move> validMoves = moveGenerator.generateMoves(gameState, isMaximizingPlayer);
        return validMoves;
    }

    // Method to simulate a move on the game board and return the resulting game state
    private GameState simulateMove(GameState gameState, Move move) {
        GameState newGameState = gameState.clone();
        newGameState.applyMove(move);
        return newGameState;
    }

    // Method to find the best move using the minimax algorithm
    public Move findBestMove(GameState gameState, int depth) {
        int bestValue = Integer.MIN_VALUE;
        Move bestMove = null;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (Move move : getAllValidMoves(gameState, true)) {
            GameState newGameState = simulateMove(gameState, move);
            int moveValue = minimax(newGameState, depth - 1, false, alpha, beta);
            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMove = move;
            }
        }
        return bestMove;
    }

}

