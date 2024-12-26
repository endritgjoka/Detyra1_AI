module com.example.detyra1_ai {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.sat4j.core;


    opens com.detyrat_ai to javafx.fxml;
    exports com.detyrat_ai;

    exports com.detyrat_ai.detyra1.social_golfers.DFS;
    opens com.detyrat_ai.detyra1.social_golfers.DFS to javafx.fxml;
    exports com.detyrat_ai.detyra1.social_golfers.DLS;
    opens com.detyrat_ai.detyra1.social_golfers.DLS to javafx.fxml;
    exports com.detyrat_ai.detyra1.sudoku;
    opens com.detyrat_ai.detyra1.sudoku to javafx.fxml;

    exports com.detyrat_ai.detyra1.latin_square;
    opens com.detyrat_ai.detyra1.latin_square to javafx.fxml;
    exports com.detyrat_ai.detyra1.social_golfers;
    opens com.detyrat_ai.detyra1.social_golfers to javafx.fxml;
    exports com.detyrat_ai.detyra2.killer_sudoku;
    opens com.detyrat_ai.detyra2.killer_sudoku to javafx.fxml;

}