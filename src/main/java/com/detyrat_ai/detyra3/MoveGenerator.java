package com.detyrat_ai.detyra3;

import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {
    GameState gameState = new GameState();
    boolean isMaximizingPlayer = true;

    // Generates all possible moves for the current player (true for white, false for black)
    public List<Move> generateMoves(GameState gameState, boolean isMaximizingPlayer) {
        List<Move> validMoves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char piece = gameState.getBoard()[i][j];
                if (piece != '.' && isPieceForPlayer(piece, isMaximizingPlayer)) {
                    // Generate possible moves for each piece
                    validMoves.addAll(generatePieceMoves(gameState, i, j, piece, isMaximizingPlayer));
                }
            }
        }
        return validMoves;
    }

    // Helper method to check if the piece belongs to the current player
    private boolean isPieceForPlayer(char piece, boolean isMaximizingPlayer) {
        if (isMaximizingPlayer) {
            return Character.isUpperCase(piece); // Upper case for white
        } else {
            return Character.isLowerCase(piece); // Lower case for black
        }
    }

    // Generate moves for a specific piece type
// Generate moves for a specific piece type
    private List<Move> generatePieceMoves(GameState gameState, int fromX, int fromY, char piece, boolean isMaximizingPlayer) {
        List<Move> moves = new ArrayList<>();

        if (Character.toLowerCase(piece) == 'p') {
            int direction = isMaximizingPlayer ? 1 : -1; // White moves up (1), Black moves down (-1)

            // Normal move (1 square)
            if (isInBounds(fromX + direction, fromY) && gameState.getBoard()[fromX + direction][fromY] == '.') {
                moves.add(new Move(fromX, fromY, fromX + direction, fromY)); // Normal move
            }

            // First move (2 squares)
            if ((fromX == 1 && !isMaximizingPlayer) || (fromX == 6 && isMaximizingPlayer)) {
                if (isInBounds(fromX + (direction * 2), fromY) && gameState.getBoard()[fromX + (direction * 2)][fromY] == '.' &&
                        gameState.getBoard()[fromX + direction][fromY] == '.') {
                    moves.add(new Move(fromX, fromY, fromX + (direction * 2), fromY)); // First move (2 squares)
                }
            }

            // Capturing move
            if (isInBounds(fromX + direction, fromY)) {
                if (fromY - 1 >= 0 && isInBounds(fromX + direction, fromY - 1) && gameState.getBoard()[fromX + direction][fromY - 1] != '.' &&
                        isOpponentPiece(gameState.getBoard()[fromX + direction][fromY - 1], isMaximizingPlayer)) {
                    moves.add(new Move(fromX, fromY, fromX + direction, fromY - 1)); // Capture left
                }
                if (fromY + 1 < 8 && isInBounds(fromX + direction, fromY + 1) && gameState.getBoard()[fromX + direction][fromY + 1] != '.' &&
                        isOpponentPiece(gameState.getBoard()[fromX + direction][fromY + 1], isMaximizingPlayer)) {
                    moves.add(new Move(fromX, fromY, fromX + direction, fromY + 1)); // Capture right
                }
            }
        }

        // Generate moves for other pieces: Rooks, Knights, Bishops, Queens, and Kings
        if (Character.toLowerCase(piece) == 'r') {
            moves.addAll(generateRookMoves(gameState, fromX, fromY, isMaximizingPlayer));
        } else if (Character.toLowerCase(piece) == 'n') {
            moves.addAll(generateKnightMoves(gameState, fromX, fromY, isMaximizingPlayer));
        } else if (Character.toLowerCase(piece) == 'b') {
            moves.addAll(generateBishopMoves(gameState, fromX, fromY, isMaximizingPlayer));
        } else if (Character.toLowerCase(piece) == 'q') {
            moves.addAll(generateQueenMoves(gameState, fromX, fromY, isMaximizingPlayer));
        } else if (Character.toLowerCase(piece) == 'k') {
            moves.addAll(generateKnightMoves(gameState, fromX, fromY, isMaximizingPlayer));
        }

        return moves;
    }

    // Check if the new position is within bounds

    // Helper method to check if the piece is an opponent's piece
    private boolean isOpponentPiece(char piece, boolean isMaximizingPlayer) {
        if (isMaximizingPlayer) {
            return Character.isLowerCase(piece); // Opponent's piece (black) for white player
        } else {
            return Character.isUpperCase(piece); // Opponent's piece (white) for black player
        }
    }

    // Generate moves for Rooks
    private List<Move> generateRookMoves(GameState gameState, int fromX, int fromY, boolean isMaximizingPlayer) {
        List<Move> moves = new ArrayList<>();
        // Rook moves in horizontal and vertical directions
        addStraightMoves(gameState, fromX, fromY, isMaximizingPlayer, moves);
        return moves;
    }

    // Generate moves for Knights
    private List<Move> generateKnightMoves(GameState gameState, int fromX, int fromY, boolean isMaximizingPlayer) {
        List<Move> moves = new ArrayList<>();
        // Knight moves in 'L' shape
        int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
        int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
        for (int i = 0; i < 8; i++) {
            int newX = fromX + dx[i];
            int newY = fromY + dy[i];
            if (isInBounds(newX, newY) && (gameState.getBoard()[newX][newY] == '.' || isOpponentPiece(gameState.getBoard()[newX][newY], isMaximizingPlayer))) {
                moves.add(new Move(fromX, fromY, newX, newY));
            }
        }
        return moves;
    }

    // Generate moves for Bishops
    private List<Move> generateBishopMoves(GameState gameState, int fromX, int fromY, boolean isMaximizingPlayer) {
        List<Move> moves = new ArrayList<>();
        // Bishop moves diagonally
        addDiagonalMoves(gameState, fromX, fromY, isMaximizingPlayer, moves);
        return moves;
    }

    // Generate moves for Queens
    private List<Move> generateQueenMoves(GameState gameState, int fromX, int fromY, boolean isMaximizingPlayer) {
        List<Move> moves = new ArrayList<>();
        // Queen moves like a Rook and Bishop combined
        addStraightMoves(gameState, fromX, fromY, isMaximizingPlayer, moves);
        addDiagonalMoves(gameState, fromX, fromY, isMaximizingPlayer, moves);
        return moves;
    }

    // Generate moves for Kings
// Assuming the method where moves are generated
    private void addKnightMoves(int fromX, int fromY, List<Move> moves) {
        int[][] knightMoves = {
                {-2, -1}, {-1, -2}, {1, -2}, {2, -1},
                {2, 1}, {1, 2}, {-1, 2}, {-2, 1}
        };

        for (int[] move : knightMoves) {
            int newX = fromX + move[0];
            int newY = fromY + move[1];

            // Ensure that newX and newY are within bounds
            if (isInBounds(newX, newY)) {
                char targetPiece = gameState.getBoard()[newX][newY];
                if (targetPiece == '.' || isOpponentPiece(targetPiece, isMaximizingPlayer)) {
                    moves.add(new Move(fromX, fromY, newX, newY));
                }
            }
        }
    }


    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    private void addStraightMoves(GameState gameState, int fromX, int fromY, boolean isMaximizingPlayer, List<Move> moves) {
        for (int i = 1; i < 8; i++) {
            // Move up
            if (isInBounds(fromX - i, fromY)) {
                char targetPiece = gameState.getBoard()[fromX - i][fromY];
                if (targetPiece == '.' || isOpponentPiece(targetPiece, isMaximizingPlayer)) {
                    moves.add(new Move(fromX, fromY, fromX - i, fromY));
                }
            }
            // Move down
            if (isInBounds(fromX + i, fromY)) {
                char targetPiece = gameState.getBoard()[fromX + i][fromY];
                if (targetPiece == '.' || isOpponentPiece(targetPiece, isMaximizingPlayer)) {
                    moves.add(new Move(fromX, fromY, fromX + i, fromY));
                }
            }
            // Move left
            if (isInBounds(fromX, fromY - i)) {
                char targetPiece = gameState.getBoard()[fromX][fromY - i];
                if (targetPiece == '.' || isOpponentPiece(targetPiece, isMaximizingPlayer)) {
                    moves.add(new Move(fromX, fromY, fromX, fromY - i));
                }
            }
            // Move right
            if (isInBounds(fromX, fromY + i)) {
                char targetPiece = gameState.getBoard()[fromX][fromY + i];
                if (targetPiece == '.' || isOpponentPiece(targetPiece, isMaximizingPlayer)) {
                    moves.add(new Move(fromX, fromY, fromX, fromY + i));
                }
            }
        }
    }

    private void addDiagonalMoves(GameState gameState, int fromX, int fromY, boolean isMaximizingPlayer, List<Move> moves) {
        for (int i = 1; i < 8; i++) {
            // Move up-left
            if (isInBounds(fromX - i, fromY - i)) {
                char targetPiece = gameState.getBoard()[fromX - i][fromY - i];
                if (targetPiece == '.' || isOpponentPiece(targetPiece, isMaximizingPlayer)) {
                    moves.add(new Move(fromX, fromY, fromX - i, fromY - i));
                }
            }
            // Move up-right
            if (isInBounds(fromX - i, fromY + i)) {
                char targetPiece = gameState.getBoard()[fromX - i][fromY + i];
                if (targetPiece == '.' || isOpponentPiece(targetPiece, isMaximizingPlayer)) {
                    moves.add(new Move(fromX, fromY, fromX - i, fromY + i));
                }
            }
            // Move down-left
            if (isInBounds(fromX + i, fromY - i)) {
                char targetPiece = gameState.getBoard()[fromX + i][fromY - i];
                if (targetPiece == '.' || isOpponentPiece(targetPiece, isMaximizingPlayer)) {
                    moves.add(new Move(fromX, fromY, fromX + i, fromY - i));
                }
            }
            // Move down-right
            if (isInBounds(fromX + i, fromY + i)) {
                char targetPiece = gameState.getBoard()[fromX + i][fromY + i];
                if (targetPiece == '.' || isOpponentPiece(targetPiece, isMaximizingPlayer)) {
                    moves.add(new Move(fromX, fromY, fromX + i, fromY + i));
                }
            }
        }
    }

}
