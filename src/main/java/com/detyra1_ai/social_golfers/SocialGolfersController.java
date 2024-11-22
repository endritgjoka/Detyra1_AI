package com.detyra1_ai.social_golfers;

import com.detyra1_ai.social_golfers.DFS.DFSView;
import com.detyra1_ai.social_golfers.DLS.DLSView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
                DFSView.display(groups, groupSize);
            } else {
                int depthLimit = Integer.parseInt(depthLimitField.getText());
                DLSView.display(groups, groupSize, depthLimit);
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
}
