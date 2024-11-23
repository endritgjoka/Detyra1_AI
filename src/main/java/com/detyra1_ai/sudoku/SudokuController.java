package com.detyra1_ai.sudoku;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SudokuController {
    private static final int SIZE = 9; // Sudoku size
    private TextField[][] cells = new TextField[SIZE][SIZE]; // Grid cells
    private String solvingMethod = "Backtracking"; // Default solving method

    @FXML
    private ComboBox<String> levelSelector;
    @FXML
    private ComboBox<String> methodSelector;

    @FXML
    public void initialize() {
        levelSelector.setValue("Easy");
        methodSelector.setValue("Backtracking");
    }

    @FXML
    private void handleGenerateButton() {
        solvingMethod = methodSelector.getValue();
        String selectedLevel = levelSelector.getValue();

        int[][] board = Utils.generateSudoku(selectedLevel);
        showSudokuBoard(board);
    }

    private void showSudokuBoard(int[][] board) {
        Stage stage = new Stage();

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(0);
        gridPane.setVgap(0);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                TextField textField = new TextField();
                textField.setPrefWidth(40);
                textField.setPrefHeight(40);
                textField.setAlignment(Pos.CENTER);

                if (board[row][col] != 0) {
                    textField.setText(String.valueOf(board[row][col]));
                    textField.setBackground(Background.fill(Color.LIGHTGREY));
                    textField.setEditable(false);
                }

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

                cells[row][col] = textField;
                gridPane.add(textField, col, row);
            }
        }
        validateBoard();

        Button solveButton = new Button("Solve Sudoku");
        solveButton.setOnAction(e -> handleSolveButton());

        Button checkButton = new Button("Check Sudoku");
        checkButton.setOnAction(e -> handleCheckButton());

        Button resetButton = new Button("Reset Board");
        resetButton.setOnAction(e -> resetBoard());

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(gridPane, solveButton, checkButton, resetButton);

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void handleSolveButton() {
        clearUserInputs();
        int[][] puzzle = Utils.getBoardFromGrid(cells);
        boolean solved = solvingMethod.equals("Backtracking")
                ? Utils.solveSudokuWithBacktracking(puzzle)
                : Utils.solveSudokuWithDFS(puzzle);
        printBoardInOneLine(puzzle);
        if (solved) {
            Utils.updateGridWithSolution(cells, puzzle);
        } else {
            showAlert(Alert.AlertType.ERROR, "Sudoku cannot be solved.");
        }
    }

    private void handleCheckButton() {
        if (Utils.isBoardFullyFilled(cells)) {
            int[][] boardFromGrid = Utils.getBoardFromGrid(cells);

            int[][] solvedBoard = Utils.getOriginalBoard(cells);
            boolean solved = solvingMethod.equals("Backtracking")
                    ? Utils.solveSudokuWithBacktracking(solvedBoard)
                    : Utils.solveSudokuWithDFS(solvedBoard);

            printBoardInOneLine(boardFromGrid);
            printBoardInOneLine(solvedBoard);
            if (solved && Utils.areBoardsSame(solvedBoard, boardFromGrid)) {
                showAlert(Alert.AlertType.INFORMATION, "The Sudoku is fully filled and valid!");
            } else {
                showAlert(Alert.AlertType.ERROR, "The Sudoku is fully filled but invalid.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "The Sudoku is not fully filled.");
        }
    }

    private void resetBoard() {
        for (TextField[] row : cells) {
            for (TextField cell : row) {
                if (cell.isEditable()) {
                    cell.clear();
                }
            }
        }
    }

    private void clearUserInputs() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].clear();
                }
            }
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void validateBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                TextField cell = cells[row][col];
                cell.textProperty().addListener((_, _, newValue) -> {
                    if (!newValue.isEmpty()) {
                        String filteredValue = newValue.replaceAll("[^\\d]", "");
                        cell.setText(filteredValue.length() > 1 ? filteredValue.substring(0, 1) : filteredValue);
                    } else {
                        cell.setText("");
                    }
                });
            }
        }

    }

    private void printBoardInOneLine(int[][] board) {
        StringBuilder boardLine = new StringBuilder();
        for (int[] row : board) {
            for (int cell : row) {
                boardLine.append(cell);
            }
        }
        System.out.println(boardLine);
    }
}
