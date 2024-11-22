package com.detyra1_ai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        ComboBox<String> selectionBox = new ComboBox<>();
        selectionBox.getItems().addAll("Detyra1", "Detyra2", "Detyra3");
        selectionBox.setPromptText("Select a task");

        Button openButton = new Button("Open");
        openButton.setDisable(true);

        selectionBox.setOnAction(e -> {
            openButton.setDisable(selectionBox.getValue() == null);
        });
        String basePath = "/com/detyra1_ai/";

        openButton.setOnAction(e -> {
            String selectedTask = selectionBox.getValue();
            if (selectedTask != null) {
                switch (selectedTask) {
                    case "Detyra1":
                        openFXML(primaryStage, basePath+"sudoku/sudoku_game.fxml", "Detyra1");
                        break;
                    case "Detyra2":
                        openFXML(primaryStage, basePath+"social_golfers/social_golfer.fxml", "Detyra2");
                        break;
                    case "Detyra3":
                        openFXML(primaryStage, basePath+"latin_square/latin_square.fxml", "Detyra3");
                        break;
                }
            }
        });

        VBox layout = new VBox(10, selectionBox, openButton);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setTitle("Select a Task");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFXML(Stage primaryStage, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
