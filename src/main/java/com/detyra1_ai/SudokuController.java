package com.detyra1_ai;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Stack;

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

        int[][] board = generateSudoku(selectedLevel);
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

                BorderStrokeStyle borderStyle = BorderStrokeStyle.SOLID;
                BorderWidths borderWidths = new BorderWidths(
                        (row == 0) ? 6 : (row % 3 == 0) ? 3 : 1, // Top border
                        (col == 8) ? 6 : (col % 3 == 2) ? 3 : 1, // Right border
                        (row == 8) ? 6 : (row % 3 == 2) ? 3 : 1, // Bottom border
                        (col == 0) ? 6 : (col % 3 == 0) ? 3 : 1  // Left border
                );

                textField.setBorder(new Border(new BorderStroke(
                        Color.BLACK,
                        borderStyle,
                        CornerRadii.EMPTY,
                        borderWidths
                )));

                cells[row][col] = textField;
                gridPane.add(textField, col, row);
            }
        }

        Button solveButton = new Button("Solve Sudoku");
        solveButton.setOnAction(e -> {
            int[][] puzzle = getBoardFromGrid();
            boolean solved;

            if (solvingMethod.equals("Backtracking")) {
                solved = solveSudokuWithBacktracking(puzzle);
            } else {
                solved = solveSudokuWithDFS(puzzle);
            }

            if (solved) {
                updateGridWithSolution(puzzle);
            } else {
                System.out.println("Sudoku nuk ka zgjidhje.");
            }
        });

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(gridPane, solveButton);

        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Retrieve board from grid
    private int[][] getBoardFromGrid() {
        int[][] board = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String text = cells[row][col].getText();
                board[row][col] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }
        return board;
    }

    // Update grid with solution
    private void updateGridWithSolution(int[][] solution) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                cells[row][col].setText(String.valueOf(solution[row][col]));
            }
        }
    }

    // Generate Sudoku board based on level
    private int[][] generateSudoku(String level) {
        int[][] board = new int[SIZE][SIZE];

        // Step 1: Generate a fully solved board using Backtracking
        solveSudokuWithBacktracking(board);

        // Step 2: Determine the number of cells to remove based on the difficulty level
        int cellsToRemove;
        switch (level) {
            case "Easy":
                cellsToRemove = 30; // More filled cells
                break;
            case "Medium":
                cellsToRemove = 40; // Moderate difficulty
                break;
            case "Hard":
                cellsToRemove = 50; // Fewer filled cells
                break;
            default:
                cellsToRemove = 30;
        }

        // Step 3: Remove numbers while ensuring the puzzle remains solvable
        while (cellsToRemove > 0) {
            int row = (int) (Math.random() * SIZE);
            int col = (int) (Math.random() * SIZE);

            if (board[row][col] != 0) {
                int temp = board[row][col];
                board[row][col] = 0;

                // Check if the puzzle is still solvable after removing the number
                int[][] copy = copyBoard(board);
                if (!solveSudokuWithBacktracking(copy)) {
                    board[row][col] = temp; // Revert if unsolvable
                } else {
                    cellsToRemove--;
                }
            }
        }

        return board;
    }


    // Check if number is valid in a cell
    private boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    // Solve Sudoku using Backtracking
    private boolean solveSudokuWithBacktracking(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;

                            if (solveSudokuWithBacktracking(board)) {
                                return true;
                            }

                            board[row][col] = 0; // Backtrack
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Solve Sudoku using DFS
    private boolean solveSudokuWithDFS(int[][] board) {
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
                copySolution(current, board);
                return true;
            }
        }
        return false;
    }

    // Copy a board
    private int[][] copyBoard(int[][] board) {
        int[][] copy = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, SIZE);
        }
        return copy;
    }

    // Copy solution to the original board
    private void copySolution(int[][] source, int[][] target) {
        for (int row = 0; row < SIZE; row++) {
            System.arraycopy(source[row], 0, target[row], 0, SIZE);
        }
    }
}
