package pongchamp.pongchamp.FX;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundEffects {
    @FXML
    ToggleButton sound;

    public void ef1() {
        String path = "Untitled3.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

    }

    public void ef2() {
        String path = "Untitled4.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

    }
}
