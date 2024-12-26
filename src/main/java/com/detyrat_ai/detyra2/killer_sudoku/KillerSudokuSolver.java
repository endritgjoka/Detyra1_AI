package com.detyrat_ai.detyra2.killer_sudoku;

public class KillerSudokuSolver {

    private static final int SIZE = 9;
    private static int[][] grid = new int[SIZE][SIZE];

    private static final Cage[] cages = {
            new Cage(new int[][]{{0, 0}, {0, 1}}, 10),
            new Cage(new int[][]{{0, 2}, {1, 2}}, 9),
            new Cage(new int[][]{{2, 0}, {2, 1}}, 10),
            new Cage(new int[][]{{1, 3}, {0, 5}}, 5),

            new Cage(new int[][]{{1, 0}, {2, 0}, {2, 1}}, 15),
            new Cage(new int[][]{{0, 6}, {0, 7}, {0, 8}}, 20),
            new Cage(new int[][]{{3, 3}, {3, 4}, {4, 3}}, 12),
            new Cage(new int[][]{{5, 5}, {5, 6}, {5, 7}, {6, 7}}, 25),
            new Cage(new int[][]{{7, 0}, {8, 0}, {8, 1}}, 12),
            new Cage(new int[][]{{6, 6}, {6, 7}, {7, 6}}, 18),
            new Cage(new int[][]{{3, 0}, {4, 0}, {5, 0}}, 17),
            new Cage(new int[][]{{8, 8}}, 5),
            new Cage(new int[][]{{4, 4}, {4, 5}}, 9),
            new Cage(new int[][]{{0, 3}, {0, 4}, {1, 3}}, 13),

    };

    public static void main(String[] args) {
        if (solveSudoku(0, 0)) {
            printSolution();
        } else {
            System.out.println("No solution found!");
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

    // Check if the current grid respects the sum and uniqueness of cages
    private static boolean isValidCage(int row, int col) {
        for (Cage cage : cages) {
            boolean isInCage = false;
            int sum = 0;
            int count = 0;  // To count how many filled cells are in this cage
            for (int[] cell : cage.positions) {
                if (cell[0] == row && cell[1] == col) {
                    isInCage = true;
                }
                if (grid[cell[0]][cell[1]] != 0) {
                    sum += grid[cell[0]][cell[1]];
                    count++;
                }
            }

            // If the current cell is part of the cage, check if sum exceeds target sum
            if (isInCage && sum > cage.targetSum) {
                return false; // Sum exceeds the target sum for this cage
            }

            // If all cells in the cage are filled, check if sum matches target sum
            if (isInCage && count == cage.positions.length && sum != cage.targetSum) {
                return false; // Sum doesn't match the target sum for this cage
            }
        }
        return true;
    }

    // Print the solved Sudoku grid
    private static void printSolution() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Define a Cage class to hold the coordinates and sum
    static class Cage {
        int[][] positions;  // Stores row, col positions of each cell in the cage
        int targetSum;      // The target sum for the cage

        public Cage(int[][] positions, int targetSum) {
            this.positions = positions;
            this.targetSum = targetSum;
        }
    }
}
