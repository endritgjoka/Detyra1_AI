package com.detyra1_ai.social_golfers.BackTracking;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class BacktrackingView {
    private static TextArea resultArea = new TextArea();
    private static boolean isStopped = false;

    private static void displaySolutionInFile3(List<List<List<Integer>>> weeks) {
        try (BufferedReader reader = new BufferedReader(new FileReader("sgp-backtracking.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                resultArea.appendText(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading sgp-backtracking.txt: " + e.getMessage());
        }

        appendWeeksToTextArea(weeks);
    }

    public static void handleStopButtonClick() {
        isStopped = true;
        displayResultsInNewWindow();
    }

    private static void displayResultsInNewWindow() {
        Stage resultStage = new Stage();
        resultStage.setTitle("Results from File");

        // Create a new TextArea to display the file content
        TextArea resultFileArea = new TextArea();
        resultFileArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("sgp-backtracking.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                resultFileArea.appendText(line + "\n"); // Append each line to the TextArea
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        VBox layout = new VBox(resultFileArea);
        Scene scene = new Scene(layout, 400, 300);

        resultStage.setScene(scene);
        resultStage.show();
    }
    private static void appendWeeksToTextArea(List<List<List<Integer>>> weeks) {
        int weekCount = 1;
        for (List<List<Integer>> week : weeks) {
            resultArea.appendText("Week " + weekCount + ":\n");
            for (List<Integer> group : week) {
                resultArea.appendText("  Formed group: " + group + "\n");
            }
            weekCount++;
        }
    }
}
