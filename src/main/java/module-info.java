module pongchamp.pongchamp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens pongchamp.pongchamp to javafx.fxml;
    exports pongchamp.pongchamp;
}