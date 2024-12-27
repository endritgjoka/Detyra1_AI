package com.detyrat_ai.detyra3;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameState gameState = new GameState();
        MiniMax minimax = new MiniMax();

        gameState.initializeMidGame();

        while (true) {
            if (gameState.isWhiteTurn()) {
                printBoard(gameState);
                System.out.println("White's turn. Enter your move (e.g., 'e2 e4'): ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the game...");
                    break;
                }

                Move playerMove = parseMove(input);
                if (playerMove != null && minimax.isMoveValid(gameState, playerMove)) {
                    gameState.applyMove(playerMove);
                    gameState.switchTurn();
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } else {
                aiMove(gameState, minimax);
            }
        }
        scanner.close();
    }

    private static void printBoard(GameState gameState) {
        char[][] board = gameState.getBoard();
        System.out.println("Current board:");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static Move parseMove(String input) {
        String[] parts = input.split(" ");
        if (parts.length == 2) {
            String from = parts[0];
            String to = parts[1];

            if (isValidMove(from) && isValidMove(to)) {
                // Adjust coordinates to be in the range 0-7
                int fromX = 8 - Integer.parseInt(from.substring(1));  // Row (Y) - fixed index range
                int fromY = from.charAt(0) - 'a';  // Column (X)
                int toX = 8 - Integer.parseInt(to.substring(1));  // Row (Y) - fixed index range
                int toY = to.charAt(0) - 'a';  // Column (X)

                if (fromX >= 0 && fromX < 8 && fromY >= 0 && fromY < 8 && toX >= 0 && toX < 8 && toY >= 0 && toY < 8) {
                    return new Move(fromX, fromY, toX, toY);
                }
            }
        }
        return null;
    }

    // Helper method to validate move input
    private static boolean isValidMove(String move) {
        if (move.length() != 2) return false;
        char file = move.charAt(0);
        char rank = move.charAt(1);
        return file >= 'a' && file <= 'h' && rank >= '1' && rank <= '8';
    }

    private static String convertToChessNotation(int x, int y) {
        char file = (char) (y + 'a');  // Convert column index to chess file (a-h)
        int rank = 8 - x;  // Convert row index to chess rank (1-8)
        return "" + file + rank;
    }

    private static void aiMove(GameState gameState, MiniMax minimax) {
        System.out.println("Black's turn (AI is making a move)...");

        List<Move> validMoves = minimax.getAllValidMoves(gameState, false);
        if (validMoves.isEmpty()) {
            System.out.println("No valid moves left for AI!");
            return;
        }

        Move bestMove = minimax.findBestMove(gameState, 3);  // Adjust depth as needed
        if (bestMove != null) {
            // Convert the move to chess notation
            String from = convertToChessNotation(bestMove.getFromX(), bestMove.getFromY());
            String to = convertToChessNotation(bestMove.getToX(), bestMove.getToY());

            // Ensure no repetition of previous moves
            if (isMoveRepeated(from, to)) {
                System.out.println("AI is repeating the move. Recalculating...");
                bestMove = minimax.findBestMove(gameState, 5);  // Recalculate with increased depth
                from = convertToChessNotation(bestMove.getFromX(), bestMove.getFromY());
                to = convertToChessNotation(bestMove.getToX(), bestMove.getToY());
            }

            System.out.println("AI attempted move: " + from + " to " + to);
            gameState.applyMove(bestMove);
            gameState.switchTurn();  // Switch turn to white after black's move
        } else {
            System.out.println("No valid moves left for AI.");
        }
    }

    private static boolean isMoveRepeated(String from, String to) {
        return false;  // Placeholder, adjust as necessary
    }

}
