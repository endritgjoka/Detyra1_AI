package com.detyrat_ai.detyra1.social_golfers.DLS;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;

public class DLSView {
    private static TextArea resultArea = new TextArea();
    private static boolean isStopped = false;

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

        try (BufferedReader reader = new BufferedReader(new FileReader("sgp-dls.txt"))) {
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

}
