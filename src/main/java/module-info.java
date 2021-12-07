module com.example.resizable_test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.resizable_test to javafx.fxml;
    exports com.example.resizable_test;
}