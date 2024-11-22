module com.example.detyra1_ai {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.detyra1_ai to javafx.fxml;
    exports com.detyra1_ai;
    exports SocialGolfersProblem;

    opens SocialGolfersProblem to javafx.fxml;
    exports SocialGolfersProblem.DFS;
    opens SocialGolfersProblem.DFS to javafx.fxml;
    exports SocialGolfersProblem.DLS;
    opens SocialGolfersProblem.DLS to javafx.fxml;
}