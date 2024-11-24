package com.detyra1_ai.sudoku;

import javafx.scene.control.TextField;

import java.util.Collections;
import java.util.Stack;

public class Utils {
    private static final int SIZE = 9;

    public static int[][] generateSudoku(String level) {
        int[][] board = new int[SIZE][SIZE];
        generateFullySolvedBoard(board);

        int cellsToRemove = switch (level) {
            case "Easy" -> 38;
            case "Medium" -> 48;
            case "Hard" -> 58;
            default -> 35;
        };

        while (cellsToRemove > 0) {
            int row = (int) (Math.random() * SIZE);
            int col = (int) (Math.random() * SIZE);

            if (board[row][col] != 0) {
                int temp = board[row][col];
                board[row][col] = 0;

                int[][] copy = copyBoard(board);
                if (!solveSudokuWithBacktracking(copy)) {
                    board[row][col] = temp;
                } else {
                    cellsToRemove--;
                }
            }
        }

        return board;
    }

    public static void generateFullySolvedBoard(int[][] board) {
        Integer[] numbers = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) numbers[i] = i + 1;

        Collections.shuffle(java.util.Arrays.asList(numbers));
        fillBoardWithRandomizedBacktracking(board, 0, 0, numbers);
    }

    public static boolean solveSudokuWithBacktracking(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;

                            if (solveSudokuWithBacktracking(board)) return true;
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean solveSudokuWithDFS(int[][] board) {
        Stack<int[][]> stack = new Stack<>();
        stack.push(board);

        while (!stack.isEmpty()) {
            int[][] current = stack.pop();

            boolean solved = true;
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    if (current[row][col] == 0) {
                        solved = false;
                        for (int num = 1; num <= SIZE; num++) {
                            if (isValid(current, row, col, num)) {
                                int[][] newBoard = copyBoard(current);
                                newBoard[row][col] = num;
                                stack.push(newBoard);
                            }
                        }
                        break;
                    }
                }
                if (!solved) break;
            }

            if (solved) {
                System.arraycopy(current, 0, board, 0, SIZE);
                return true;
            }
        }

        return false;
    }

    public static boolean isBoardFullyFilled(TextField[][] cells) {
        for (TextField[] row : cells) {
            for (TextField cell : row) {
                if (cell.getText().isEmpty()) return false;
            }
        }
        return true;
    }

    public static boolean areBoardsSame(int[][] board1, int[][] board2) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board1[row][col] != board2[row][col]) return false;
            }
        }
        return true;
    }

    public static int[][] copyBoard(int[][] board) {
        int[][] copy = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            System.arraycopy(board[row], 0, copy[row], 0, SIZE);
        }
        return copy;
    }

    public static int[][] getBoardFromGrid(TextField[][] cells) {
        int[][] board = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String text = cells[row][col].getText();
                board[row][col] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }
        return board;
    }

    public static int[][] getOriginalBoard(TextField[][] cells) {
        int[][] board = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = cells[row][col].isEditable() ? 0 : Integer.parseInt(cells[row][col].getText());
            }
        }
        return board;
    }


    public static void updateGridWithSolution(TextField[][] cells, int[][] solution) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].setText(String.valueOf(solution[row][col]));
                }
            }
        }
    }

    private static boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num || board[row / 3 * 3 + i / 3][col / 3 * 3 + i % 3] == num)
                return false;
        }
        return true;
    }

    private static boolean fillBoardWithRandomizedBacktracking(int[][] board, int row, int col, Integer[] numbers) {
        if (row == SIZE) return true;
        if (col == SIZE) return fillBoardWithRandomizedBacktracking(board, row + 1, 0, numbers);

        for (int num : numbers) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;
                if (fillBoardWithRandomizedBacktracking(board, row, col + 1, numbers)) return true;
                board[row][col] = 0;
            }
        }

        return false;
    }

}
