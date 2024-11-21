module com.example.detyra1_ai {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.detyra1_ai to javafx.fxml;
    exports com.detyra1_ai;
}