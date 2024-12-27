package com.detyrat_ai.detyra3;

import java.util.List;

public class MiniMax {
    public Move findBestMove(GameState state, int depth, boolean isMaximizingPlayer) {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        Move bestMove = null;
        List<Move> moves = state.generateMoves();

        for (Move move : moves) {
            char capturedPiece = state.board[move.toRow][move.toCol];
            state.applyMove(move);
            int value = minimax(state, depth - 1, alpha, beta, !isMaximizingPlayer);
            state.undoMove(move, capturedPiece);

            if (isMaximizingPlayer) {
                if (value > alpha) {
                    alpha = value;
                    bestMove = move;
                }
            } else {
                if (value < beta) {
                    beta = value;
                    bestMove = move;
                }
            }
        }
        return bestMove;
    }

    private int minimax(GameState state, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
        if (depth == 0) {
            return state.evaluate();
        }

        List<Move> moves = state.generateMoves();
        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : moves) {
                char capturedPiece = state.board[move.toRow][move.toCol];
                state.applyMove(move);
                int eval = minimax(state, depth - 1, alpha, beta, false);
                state.undoMove(move, capturedPiece);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : moves) {
                char capturedPiece = state.board[move.toRow][move.toCol];
                state.applyMove(move);
                int eval = minimax(state, depth - 1, alpha, beta, true);
                state.undoMove(move, capturedPiece);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }
}