package SocialGolfersProblem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SocialGolfersProblem/socialgolferproblem.fxml"));
        VBox root = loader.load();

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Social Golfers Problem");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
