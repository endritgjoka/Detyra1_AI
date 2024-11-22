package com.detyra1_ai.social_golfers.DFS;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class DFSView {
    public static void display(int groups, int players) {
        Stage stage = new Stage();
        stage.setTitle("DFS Solution");

        List<List<List<Integer>>> weeks = SocialGolfersDFS.solve(groups, players);

        TextArea resultArea = new TextArea();
        for (int w = 0; w < weeks.size(); w++) {
            resultArea.appendText("Week " + (w + 1) + ":\n");
            List<List<Integer>> groupsForWeek = weeks.get(w);
            for (int g = 0; g < groupsForWeek.size(); g++) {
                resultArea.appendText("  Group " + (g + 1) + ": " + groupsForWeek.get(g) + "\n");
            }
        }

        VBox layout = new VBox(resultArea);
        Scene scene = new Scene(layout, 400, 300);

        stage.setScene(scene);
        stage.show();
    }
}