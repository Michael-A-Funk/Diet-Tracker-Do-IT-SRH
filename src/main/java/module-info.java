module com.example.diet_tracker_doit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;


    opens com.srh.diet_tracker to javafx.fxml;
    exports com.srh.diet_tracker;
}