module com.example.newdemo1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens com.example.newdemo1 to javafx.fxml;
    exports com.example.newdemo1;
}