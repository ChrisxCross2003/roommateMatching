module com.example.roommatematching {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.roommatematching to javafx.fxml;
    exports com.example.roommatematching;
}