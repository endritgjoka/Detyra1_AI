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

    public static void display(int groups, int players) {
        Stage stage = new Stage();
        stage.setTitle("Backtracking Solution");

        List<List<List<Integer>>> weeks = SocialGolfersBacktracking.solve(groups, players);

        resultArea.setEditable(false);

        displaySolutionInFile3(weeks);

        VBox layout = new VBox(resultArea);
        Scene scene = new Scene(layout, 400, 300);

        stage.setScene(scene);
        stage.show();
    }

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
