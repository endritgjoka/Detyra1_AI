package com.detyrat_ai.detyra2.killer_sudoku;

import java.util.Scanner;

public class KillerSudokuSolver {

    private static final int SIZE = 9;
    private static int[][] grid = new int[SIZE][SIZE];
    private static Cage[] cages;

    public static void main(String[] args) {
        System.out.println("Choose level of killer sudoku: ");
        Scanner scanner = new Scanner(System.in);
        String difficulty = scanner.nextLine();
        initializeGame(difficulty);

        if (solveSudoku(0, 0)) {
            printSolution();
        } else {
            System.out.println("No solution found!");
        }
    }

    private static void initializeGame(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                System.out.println("Easy level: ");
                cages = new Cage[]{
                        new Cage(new int[][]{{0, 0}, {0, 1}}, 10),
                        new Cage(new int[][]{{0, 2}, {1, 2}}, 9),
                        new Cage(new int[][]{{1, 0}, {2, 0}}, 7),
                        new Cage(new int[][]{{2, 1}, {2, 2}}, 11)
                };
                break;
            case "medium":
                System.out.println("Medium level: ");
                cages = new Cage[]{
                        new Cage(new int[][]{{0, 0}, {0, 1}}, 10),
                        new Cage(new int[][]{{0, 2}, {1, 2}}, 9),
                        new Cage(new int[][]{{2, 0}, {2, 1}}, 10),
                        new Cage(new int[][]{{1, 3}, {0, 5}}, 5),
                        new Cage(new int[][]{{0, 6}, {0, 7}, {0, 8}}, 20),
                        new Cage(new int[][]{{4, 4}, {4, 5}}, 9),
                };
                grid[0][0] = 4;
                break;

            case "hard":
                System.out.println("Hard level: ");
                cages = new Cage[]{
                        new Cage(new int[][]{{0, 0}, {0, 1}}, 5),
                        new Cage(new int[][]{{0, 2}, {1, 2}}, 8),
                        new Cage(new int[][]{{2, 0}, {2, 1}}, 9),
                        new Cage(new int[][]{{3, 3}, {4, 4}}, 12),
                        new Cage(new int[][]{{5, 5}, {5, 6}}, 7),
                        new Cage(new int[][]{{7, 7}, {8, 8}}, 3),
                };
                break;

            default:
                throw new IllegalArgumentException("Invalid difficulty level. Choose easy, medium, or hard.");
        }
    }

    private static boolean solveSudoku(int row, int col) {
        if (row == SIZE) {
            return true;
        }

        if (col == SIZE) {
            return solveSudoku(row + 1, 0);
        }

        if (grid[row][col] != 0) {
            return solveSudoku(row, col + 1);
        }

        for (int num = 1; num <= SIZE; num++) {
            grid[row][col] = num;

            if (isValid(row, col) && isValidCage(row, col)) {
                if (solveSudoku(row, col + 1)) {
                    return true;
                }
            }

            grid[row][col] = 0;
        }

        return false;
    }

    private static boolean isValid(int row, int col) {
        for (int i = 0; i < SIZE; i++) {
            if (i != col && grid[row][i] == grid[row][col]) {
                return false;
            }
            if (i != row && grid[i][col] == grid[row][col]) {
                return false;
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if ((i != row || j != col) && grid[i][j] == grid[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean isValidCage(int row, int col) {
        for (Cage cage : cages) {
            boolean isInCage = false;
            int sum = 0;
            int count = 0;
            for (int[] cell : cage.positions) {
                if (cell[0] == row && cell[1] == col) {
                    isInCage = true;
                }
                if (grid[cell[0]][cell[1]] != 0) {
                    sum += grid[cell[0]][cell[1]];
                    count++;
                }
            }

            if (isInCage && sum > cage.targetSum) {
                return false;
            }

            if (isInCage && count == cage.positions.length && sum != cage.targetSum) {
                return false;
            }
        }
        return true;
    }

    private static void printSolution() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    static class Cage {
        int[][] positions;
        int targetSum;

        public Cage(int[][] positions, int targetSum) {
            this.positions = positions;
            this.targetSum = targetSum;
        }
    }
}
