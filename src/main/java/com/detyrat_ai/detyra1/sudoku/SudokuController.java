package com.detyrat_ai.detyra1.sudoku;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class SudokuController {
    private static final int SIZE = 9; // Sudoku size
    private TextField[][] cells = new TextField[SIZE][SIZE]; // Grid cells

    @FXML
    private ComboBox<String> levelSelector;
    @FXML
    private ComboBox<String> methodSelector;
    @FXML
    private GridPane sudokuGrid;

    @FXML
    public void initialize() {
        levelSelector.setValue("Easy");
        methodSelector.setValue("Backtracking");

        // Populate the grid with editable cells
        initializeSudokuGrid();
    }

    private void initializeSudokuGrid() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                TextField textField = new TextField();
                textField.setPrefWidth(40);
                textField.setPrefHeight(40);
                textField.setAlignment(Pos.CENTER);
                textField.setEditable(false);

                // Add borders for better
                BorderWidths borderWidths = new BorderWidths(
                        (row == 0) ? 6 : (row % 3 == 0) ? 3 : 1,
                        (col == 8) ? 6 : (col % 3 == 2) ? 3 : 1,
                        (row == 8) ? 6 : (row % 3 == 2) ? 3 : 1,
                        (col == 0) ? 6 : (col % 3 == 0) ? 3 : 1
                );

                textField.setBorder(new Border(new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        borderWidths
                )));

                // Input validation
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.matches("\\d*") || newValue.length() > 1) {
                        textField.setText(oldValue);
                    }
                });

                cells[row][col] = textField;
                sudokuGrid.add(textField, col, row);
            }
        }
    }

    @FXML
    private void handleGenerateButton() {
        String selectedLevel = levelSelector.getValue();
        int[][] board = Utils.generateSudoku(selectedLevel);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(board[row][col]));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(Background.fill(Color.LIGHTGREY));
                } else {
                    cells[row][col].clear();
                    cells[row][col].setEditable(true);
                    cells[row][col].setBackground(Background.fill(Color.WHITE));
                }
            }
        }
    }

    @FXML
    private void handleSolveButton() {
        int[][] puzzle = Utils.getBoardFromGrid(cells);
        boolean solved = methodSelector.getValue().equals("Backtracking")
                ? Utils.solveSudokuWithBacktracking(puzzle)
                : Utils.solveSudokuWithDFS(puzzle);

        if (solved) {
            Utils.updateGridWithSolution(cells, puzzle);
        } else {
            showAlert(Alert.AlertType.ERROR, "Sudoku cannot be solved.");
        }
    }

    @FXML
    private void handleCheckButton() {
        if (Utils.isBoardFullyFilled(cells)) {
            int[][] boardFromGrid = Utils.getBoardFromGrid(cells);

            int[][] solvedBoard = Utils.getOriginalBoard(cells);
            boolean solved = methodSelector.getValue().equals("Backtracking")
                    ? Utils.solveSudokuWithBacktracking(solvedBoard)
                    : Utils.solveSudokuWithDFS(solvedBoard);

            if (solved && Utils.areBoardsSame(solvedBoard, boardFromGrid)) {
                showAlert(Alert.AlertType.INFORMATION, "The Sudoku is fully filled and valid!");
            } else {
                showAlert(Alert.AlertType.ERROR, "The Sudoku is fully filled but invalid.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "The Sudoku is not fully filled.");
        }
    }

    @FXML
    private void resetBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].clear();
                cells[row][col].setEditable(true);
                cells[row][col].setBackground(Background.fill(Color.WHITE));
            }
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait();
    }
}
