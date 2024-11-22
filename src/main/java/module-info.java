module com.example.detyra1_ai {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.detyra1_ai to javafx.fxml;
    exports com.detyra1_ai;
    exports com.detyra1_ai.social_golfers;

    opens com.detyra1_ai.social_golfers to javafx.fxml;
    exports com.detyra1_ai.social_golfers.DFS;
    opens com.detyra1_ai.social_golfers.DFS to javafx.fxml;
    exports com.detyra1_ai.social_golfers.DLS;
    opens com.detyra1_ai.social_golfers.DLS to javafx.fxml;
    exports com.detyra1_ai.sudoku;
    opens com.detyra1_ai.sudoku to javafx.fxml;

    exports com.detyra1_ai.latin_square;
    opens com.detyra1_ai.latin_square to javafx.fxml;
}