module Pongchamp {

    requires javafx.controls;
    requires javafx.fxml;


    requires javafx.graphics;


    requires java.desktop;
    requires javafx.media;
    opens pongchamp.pongchamp to javafx.fxml;
    exports pongchamp.pongchamp;
}

