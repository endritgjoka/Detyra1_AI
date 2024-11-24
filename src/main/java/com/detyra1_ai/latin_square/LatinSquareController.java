package com.detyra1_ai.latin_square;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LatinSquareController {

    @FXML
    private TextField sizeInput;

    @FXML
    private ComboBox<String> solvingMethod;

    @FXML
    private TextArea outputArea;

    @FXML
    private Label errorLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    private int[][] lastGeneratedGrid;

    @FXML
    private void initialize() {
        // Initialize the ComboBox with solving methods
        solvingMethod.getItems().addAll("Backtracking", "IDDFS");
        solvingMethod.setValue("Backtracking"); // Default method
    }

    @FXML
    private void generateLatinSquare() {
        errorLabel.setVisible(false);
        outputArea.clear();

        try {
            int n = Integer.parseInt(sizeInput.getText().trim());
            if (n <= 0) {
                showError("Please enter a positive integer for n.");
                return;
            }

            String selectedMethod = solvingMethod.getValue();
            lastGeneratedGrid = new int[n][n];
            outputArea.appendText("Generating Latin Square using " + selectedMethod + "...\n");
            progressIndicator.setVisible(true); // Show progress indicator

            if ("IDDFS".equals(selectedMethod)) {
                runIDDFSInBackground(n);
            } else if ("Backtracking".equals(selectedMethod)) {
                runBacktrackingInBackground(n);
            } else {
                showError("Invalid solving method selected.");
                progressIndicator.setVisible(false); // Hide progress indicator
            }
        } catch (NumberFormatException e) {
            showError("Invalid input. Please enter a valid integer.");
            progressIndicator.setVisible(false); // Hide progress indicator
        }
    }
    private void runBacktrackingInBackground(int n) {
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                return solveUsingBacktracking(lastGeneratedGrid, n);
            }
        };

        task.setOnSucceeded(event -> {
            progressIndicator.setVisible(false); // Hide progress indicator
            if (task.getValue()) {
                outputArea.setText(formatGrid(lastGeneratedGrid, n));
            } else {
                showError("Could not generate a Latin Square for the given size.");
            }
        });

        task.setOnFailed(event -> {
            progressIndicator.setVisible(false); // Hide progress indicator
            showError("An error occurred while generating the Latin Square.");
        });

        new Thread(task).start();
    }


    private void runIDDFSInBackground(int n) {
        Task<Boolean> task = new Task<>() {
            @Override
            protected Boolean call() {
                return solveUsingIDDFS(lastGeneratedGrid, n);
            }
        };

        task.setOnSucceeded(event -> {
            progressIndicator.setVisible(false); // Hide progress indicator on success
            if (task.getValue()) {
                outputArea.setText(formatGrid(lastGeneratedGrid, n));
            } else {
                showError("Could not generate a Latin Square for the given size.");
            }
        });

        task.setOnFailed(event -> {
            progressIndicator.setVisible(false); // Hide progress indicator on failure
            showError("An error occurred while generating the Latin Square.");
        });

        progressIndicator.setVisible(true); // Show progress indicator before starting
        new Thread(task).start();
    }


    @FXML
    private void validateSolution() {
        errorLabel.setVisible(false);

        if (lastGeneratedGrid == null) {
            showError("No Latin Square generated to validate.");
            return;
        }

        if (isValidLatinSquare(lastGeneratedGrid)) {
            outputArea.appendText("\nThe solution is a valid Latin Square.");
        } else {
            outputArea.appendText("\nThe solution is NOT a valid Latin Square.");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private boolean isValidLatinSquare(int[][] grid) {
        int n = grid.length;

        for (int i = 0; i < n; i++) {
            boolean[] rowCheck = new boolean[n + 1];
            boolean[] colCheck = new boolean[n + 1];

            for (int j = 0; j < n; j++) {
                if (rowCheck[grid[i][j]] || colCheck[grid[j][i]]) {
                    return false;
                }

                rowCheck[grid[i][j]] = true;
                colCheck[grid[j][i]] = true;
            }
        }
        return true;
    }

    // Backtracking solving method
    private boolean solveUsingBacktracking(int[][] grid, int n) {
        return solveBacktracking(grid, n, 0, 0);
    }

    private boolean solveBacktracking(int[][] grid, int n, int row, int col) {
        if (row == n) {
            return true;
        }

        if (col == n) {
            return solveBacktracking(grid, n, row + 1, 0);
        }

        for (int num = 1; num <= n; num++) {
            if (isValid(grid, row, col, num, n)) {
                grid[row][col] = num;
                if (solveBacktracking(grid, n, row, col + 1)) {
                    return true;
                }
                grid[row][col] = 0;
            }
        }

        return false;
    }

    // IDDFS solving method
    private boolean solveUsingIDDFS(int[][] grid, int n) {
        boolean[][] rowUsed = new boolean[n][n + 1];
        boolean[][] colUsed = new boolean[n][n + 1];

        for (int depth = 0; depth <= n * n; depth++) {
            if (iddfs(grid, n, 0, 0, depth, rowUsed, colUsed)) {
                return true;
            }
        }
        return false;
    }


    private boolean iddfs(int[][] grid, int n, int row, int col, int depth, boolean[][] rowUsed, boolean[][] colUsed) {
        if (depth < 0) {
            return false;
        }

        if (row == n) {
            return true;
        }

        if (col == n) {
            return iddfs(grid, n, row + 1, 0, depth, rowUsed, colUsed);
        }

        for (int num = 1; num <= n; num++) {
            if (!rowUsed[row][num] && !colUsed[col][num]) {
                grid[row][col] = num;
                rowUsed[row][num] = true;
                colUsed[col][num] = true;

                if (iddfs(grid, n, row, col + 1, depth - 1, rowUsed, colUsed)) {
                    return true;
                }

                grid[row][col] = 0;
                rowUsed[row][num] = false;
                colUsed[col][num] = false;
            }
        }

        return false;
    }


    private boolean isValid(int[][] grid, int row, int col, int num, int n) {
        for (int i = 0; i < n; i++) {
            if (grid[row][i] == num || grid[i][col] == num) {
                return false;
            }
        }
        return true;
    }

    private String formatGrid(int[][] grid, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(grid[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
