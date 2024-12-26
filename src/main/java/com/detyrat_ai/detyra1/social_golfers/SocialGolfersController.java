package com.detyrat_ai.detyra1.social_golfers;

import com.detyrat_ai.detyra1.social_golfers.BackTracking.BacktrackingTest;
import com.detyrat_ai.detyra1.social_golfers.BackTracking.SocialGolfersBacktracking;
import com.detyrat_ai.detyra1.social_golfers.DFS.DFSView;
import com.detyrat_ai.detyra1.social_golfers.DFS.SocialGolfersDFS;
import com.detyrat_ai.detyra1.social_golfers.DLS.DLSView;
import com.detyrat_ai.detyra1.social_golfers.BackTracking.BacktrackingView;  // Import BacktrackingView
import com.detyrat_ai.detyra1.social_golfers.DLS.SocialGolfersDLS;
import com.detyrat_ai.detyra1.social_golfers.DLS.SocialGolfersDLSTest;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.detyrat_ai.detyra1.social_golfers.DFS.SocialGolfersDFSTest;

import java.util.List;

public class SocialGolfersController {
    @FXML
    private ComboBox<String> algorithmComboBox;
    @FXML
    private TextField playersField;  // Total number of players (P * G)
    @FXML
    private TextField groupSizeField; // Number of players per group (P)
    @FXML
    private TextField depthLimitField;

    @FXML
    public void initialize() {
        algorithmComboBox.setValue("DFS");
    }

    @FXML
    public void handleAlgorithmSelection() {
        String selectedAlgorithm = algorithmComboBox.getValue();
        if ("DLS".equals(selectedAlgorithm)) {
            depthLimitField.setVisible(true);
        } else {
            depthLimitField.setVisible(false);
        }
    }

    @FXML
    public void handleSolveButtonClick() {
        try {
            // Get inputs
            int totalPlayers = Integer.parseInt(playersField.getText());
            int groupSize = Integer.parseInt(groupSizeField.getText());

            int groups = totalPlayers / groupSize;
            if (totalPlayers % groupSize != 0) {
                showAlert("Input Error", "Total number of players must be divisible by the group size.");
                return;
            }

            String selectedAlgorithm = algorithmComboBox.getValue();
            if ("DFS".equals(selectedAlgorithm)) {
                new Thread(() -> {
                    List<List<List<Integer>>> weeks = SocialGolfersDFS.solve(groups, groupSize);

                }).start();
            } else if ("DLS".equals(selectedAlgorithm)) {
                int depthLimit = Integer.parseInt(depthLimitField.getText());
                new Thread(() -> {
                    List<List<List<Integer>>> weeks = SocialGolfersDLS.solve(groups, groupSize,depthLimit);
                }).start();
            } else if ("Backtracking".equals(selectedAlgorithm)) {
                new Thread(() -> {
                    List<List<List<Integer>>> weeks = SocialGolfersBacktracking.solve(groups, groupSize);
                }).start();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter valid numbers for players and group size.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An unexpected error occurred");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void handleStopButtonClick() {
        if ("DLS".equals(algorithmComboBox.getValue())) {
            DLSView.handleStopButtonClick();
        } else if ("DFS".equals(algorithmComboBox.getValue())) {
            DFSView.handleStopButtonClick();
        }else if("Backtracking".equals(algorithmComboBox.getValue())) {
            BacktrackingView.handleStopButtonClick();
        }
    }

    @FXML
    public void testSolution() {
        int groups = Integer.parseInt(playersField.getText()) / Integer.parseInt(groupSizeField.getText());
        int players = Integer.parseInt(groupSizeField.getText());

        // Run the validation method
        if ("DFS".equals(algorithmComboBox.getValue())) {
            boolean isValid = SocialGolfersDFSTest.validateSolution("sgp-dfs.txt", groups, players);
            String resultMessage = isValid ? "The solution is valid!" : "The solution is not valid!";
            Alert alert = new Alert(isValid ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Test Solution");
            alert.setHeaderText(null);
            alert.setContentText(resultMessage);
            alert.showAndWait();
        } else if ("DLS".equals(algorithmComboBox.getValue())) {
            boolean isValid = SocialGolfersDLSTest.validateSolution("sgp-dfs.txt", groups, players);
            String resultMessage = isValid ? "The solution is valid!" : "The solution is not valid!";
            Alert alert = new Alert(isValid ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
            alert.setTitle("Test Solution");
            alert.setHeaderText(null);
            alert.setContentText(resultMessage);
            alert.showAndWait();
        } else if ("Backtracking".equals(algorithmComboBox.getValue())) {
        boolean isValid = BacktrackingTest.validateSolution("sgp-dfs.txt", groups, players);
        String resultMessage = isValid ? "The solution is valid!" : "The solution is not valid!";
        Alert alert = new Alert(isValid ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle("Test Solution");
        alert.setHeaderText(null);
        alert.setContentText(resultMessage);
        alert.showAndWait();
    }
    }

}
