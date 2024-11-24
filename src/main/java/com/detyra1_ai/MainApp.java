package com.detyra1_ai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        String basePath = "/com/detyra1_ai/";

        Button buttonDetyra1 = createTaskButton("Sudoku", "sudoku_icon.png", e ->
                openFXML( basePath + "sudoku/sudoku_game.fxml", "Sudoku Game"));
        Button buttonDetyra2 = createTaskButton("Social Golfers", "golf_icon.png", e ->
                openFXML( basePath + "social_golfers/social_golfer.fxml", "Social Golfers Problem"));
        Button buttonDetyra3 = createTaskButton("Latin Square", "latin_square.png", e ->
                openFXML( basePath + "latin_square/latin_square.fxml", "Latin Squuare"));

        VBox layout = new VBox(20, buttonDetyra1, buttonDetyra2, buttonDetyra3);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setTitle("Select a Task");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Make the window non-resizable
        primaryStage.show();
    }


    private Button createTaskButton(String text, String iconName, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("/com/detyra1_ai/icons/" + iconName)));
        icon.setFitWidth(20); // Set icon size
        icon.setFitHeight(20);

        Button button = new Button(text, icon);
        button.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
        button.setOnAction(action);

        return button;
    }

    private void openFXML(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage newStage = new Stage();
            newStage.setTitle(title);
            newStage.setScene(scene);
            newStage.setResizable(false);
            newStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
