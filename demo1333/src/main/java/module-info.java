module com.example.demo1333 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.base;

    opens com.example.demo1333 to javafx.fxml;
    exports com.example.demo1333;
}