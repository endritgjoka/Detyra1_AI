package com.detyrat_ai.detyra3;

public class Main {
    public static void main(String[] args) {
        GameState initialState = new GameState();
        initialState.initializeMidGame(); // Initialize the mid-game position

        MiniMax miniMax = new MiniMax();
        for (int i = 0; i < 3; i++) {
            System.out.println("Turn " + (i + 1) + ":");

            // MAX move
            Move bestMove = miniMax.findBestMove(initialState, 3, true);
            System.out.println("MAX chooses: " + bestMove);
            initialState.applyMove(bestMove);
            printBoard(initialState);

            // MIN move
            bestMove = miniMax.findBestMove(initialState, 3, false);
            System.out.println("MIN chooses: " + bestMove);
            initialState.applyMove(bestMove);
            printBoard(initialState);
        }
    }

    private static void printBoard(GameState state) {
        for (char[] row : state.board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}