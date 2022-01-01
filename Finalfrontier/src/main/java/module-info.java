module Pongchamp {

    requires javafx.controls;
    requires javafx.fxml;


    requires javafx.graphics;


    requires java.desktop;
    requires javafx.media;
    opens pongchamp to javafx.fxml;
    exports pongchamp;
}

