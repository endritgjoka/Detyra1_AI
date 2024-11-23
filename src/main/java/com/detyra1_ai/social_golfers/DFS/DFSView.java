package com.detyra1_ai.social_golfers.DFS;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DFSView {
    private static TextArea resultArea = new TextArea();  // Make it static to update from other methods

    public static void display(int groups, int players) {
        Stage stage = new Stage();
        stage.setTitle("DFS Solution");

        // Add a listener to ensure the TextArea updates with each new group
        List<List<List<Integer>>> weeks = SocialGolfersDFS.solve(groups, players);

        // Setup TextArea
        resultArea.setEditable(false);
        loadFileContentToTextArea(); // Load the file content into the TextArea

        VBox layout = new VBox(resultArea);
        Scene scene = new Scene(layout, 400, 300);

        stage.setScene(scene);
        stage.show();
    }

    // This method can be used to append groups to the TextArea in real-time
    public static void updateGroupInGUI(List<Integer> group) {
        resultArea.appendText("Formed group: " + group + "\n");
    }

    // New method to read the content of the file and load it into the TextArea
    private static void loadFileContentToTextArea() {
        try (BufferedReader reader = new BufferedReader(new FileReader("sgp-dfs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                resultArea.appendText(line + "\n"); // Append each line to the TextArea
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
