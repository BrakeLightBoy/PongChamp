module com.example.translationtest {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.translationtest to javafx.fxml;
    exports com.example.translationtest;
}