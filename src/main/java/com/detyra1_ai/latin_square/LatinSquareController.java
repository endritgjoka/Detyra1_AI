package com.detyra1_ai.latin_square;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class LatinSquareController {

    @FXML
    private TextField sizeInput;

    @FXML
    private TextArea outputArea;

    @FXML
    private Label errorLabel;

    @FXML
    private void generateLatinSquare() {
        errorLabel.setVisible(false); // Hide error label initially
        outputArea.clear(); // Clear previous results

        try {
            int n = Integer.parseInt(sizeInput.getText().trim());
            if (n <= 0) {
                showError("Please enter a positive integer for n.");
                return;
            }

            int[][] grid = new int[n][n];
            if (generateLatinSquare(grid, n)) {
                outputArea.setText(formatGrid(grid, n));
            } else {
                showError("Could not generate a Latin Square for the given size.");
            }
        } catch (NumberFormatException e) {
            showError("Invalid input. Please enter a valid integer.");
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private boolean solve(int[][] grid, int n, int row, int col) {
        if (row == n) {
            // All rows are filled, the Latin Square is complete
            return true;
        }

        if (col == n) {
            // Move to the next row
            return solve(grid, n, row + 1, 0);
        }

        for (int num = 1; num <= n; num++) {
            if (isValid(grid, row, col, num, n)) {
                grid[row][col] = num; // Place the number
                if (solve(grid, n, row, col + 1)) { // Recurse to next cell
                    return true;
                }
                grid[row][col] = 0; // Backtrack
            }
        }

        return false; // No valid number found
    }

    private boolean generateLatinSquare(int[][] grid, int n) {
        return solve(grid, n, 0, 0); // Start solving from the top-left corner
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
