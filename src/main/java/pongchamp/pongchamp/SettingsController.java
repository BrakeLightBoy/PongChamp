package pongchamp.pongchamp;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends MainController implements Initializable {
    @FXML
    Pane slider;
    @FXML
    AnchorPane mainPane;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ToggleButton musicB;
    @FXML
    private ColorPicker ballColour;
    @FXML
    private ColorPicker paddle1Colour;
    @FXML
    private ColorPicker paddle2Colour;
    @FXML
    private Circle ballPreview;
    @FXML
    private Rectangle paddle1Preview;
    @FXML
    private Rectangle paddle2Preview;
    @Override
    public void exitPage(ActionEvent event)  {
        super.exitPage(event);
    }
    @Override
    public void gameMode(MouseEvent event) {
        super.gameMode(event);
    }
    @Override
    public void settingsPage(MouseEvent event) {
        super.settingsPage(event);
}




    @FXML
    public void playHit(ActionEvent event) {
        String path = "/Users/umair/Desktop/merge test/Finalfrontier/src/main/resources/com/example/Sounds/Celebration by Kool and the Gang with.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
            }
        });
        musicB.setOnAction(e -> {
            if (musicB.isSelected()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
        });
    }
// colour picker needs to be assigned to paddle/ball etc in back-end
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paddle2Colour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                paddle2Preview.setFill(paddle2Colour.getValue());
            }
        });
        paddle1Colour.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                paddle1Preview.setFill(paddle1Colour.getValue());
            }
        });
        ballColour.setOnAction(new EventHandler<ActionEvent>() {
            @FXML
            @Override
            public void handle(ActionEvent event) {
                ballPreview.setFill(ballColour.getValue());
            }
        });
    }
    @FXML
    private void handleColourPicker(ActionEvent event) {


    }

}

/*
    }
    // this dark/light mode test was working before but not with new changes, not priority to look at it now.
    @com.example.finalfrontier.FXML
    public void changeMode(ActionEvent event) {
        isLightMode = !isLightMode;
        if (darklight.isPressed()) {
            setLightMode();
        } else {
            setDarkMode();
        }
    }

    private void setLightMode() {
        mainPane.getStylesheets().remove(MainController.class.getResource("dark.css").toString());
        mainPane.getStylesheets().add(MainController.class.getResource("light.css").toExternalForm());
        Image image = new Image(MainController.class.getResource("sunfull.PNG").toExternalForm());
        img.setImage(image);

    }

    private void setDarkMode() {
        mainPane.getStylesheets().remove(MainController.class.getResource("light.css").toExternalForm());
        mainPane.getStylesheets().add(MainController.class.getResource("dark.css").toExternalForm());
        Image image = new Image(MainController.class.getResource("sunempty.PNG").toExternalForm());
        img.setImage(image);
    }
 */

