module Pongchamp {

    requires javafx.controls;
    requires javafx.fxml;


    requires javafx.graphics;


    requires java.desktop;
    requires javafx.media;
    requires com.google.gson;
    opens pongchamp.pongchamp to javafx.fxml;
    opens pongchamp.pongchamp.model to com.google.gson;
    opens pongchamp.pongchamp.model.math to com.google.gson;
    exports pongchamp.pongchamp;
}

