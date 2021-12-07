module pongchamp.pongchamp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires javafx.media;

    opens pongchamp.pongchamp to javafx.fxml;
    exports pongchamp.pongchamp;
}