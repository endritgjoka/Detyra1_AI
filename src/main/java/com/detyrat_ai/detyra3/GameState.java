package com.detyrat_ai.detyra3;

public class GameState {
    private char[][] board;
    private boolean isWhiteTurn;

    public GameState() {
        board = new char[8][8];
        isWhiteTurn = true;
    }

    public void initializeMidGame() {
        String[] setup = {
                "rnbqkbnr",
                "pppppppp",
                "........",
                "....P...",
                "...N....",
                "...P....",
                "PPPP.PPP",
                "RNBQKBNR"
        };

        for (int i = 0; i < 8; i++) {
            board[i] = setup[i].toCharArray();
        }
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public void switchTurn() {
        isWhiteTurn = !isWhiteTurn;
    }

    public GameState clone() {
        GameState clonedState = new GameState();
        for (int i = 0; i < 8; i++) {
            clonedState.board[i] = this.board[i].clone();
        }
        clonedState.isWhiteTurn = this.isWhiteTurn;
        return clonedState;
    }

    public void applyMove(Move move) {
        int fromX = move.getFromX();
        int fromY = move.getFromY();
        int toX = move.getToX();
        int toY = move.getToY();

        char piece = board[fromX][fromY];
        board[toX][toY] = piece;
        board[fromX][fromY] = '.';
    }
}
