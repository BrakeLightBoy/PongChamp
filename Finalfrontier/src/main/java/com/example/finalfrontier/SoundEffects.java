package com.example.finalfrontier;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundEffects {
// to be implemented in back-end again due to new changes, button functionality is currently off
    @FXML
    ToggleButton sound;
    @FXML
    Boolean isSound = true;
    @FXML
    public AudioClip clip = null;

    public void ef1() {
        String path = "src/main/resources/pongchamp/pongchamp/Sounds/6.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

    }

    public void ef2() {
        String path = "src/main/resources/pongchamp/pongchamp/Sounds/Untitled4.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer1 = new MediaPlayer(media);
        mediaPlayer1.play();

    }

    public void clip() {
        String path = "src/main/resources/pongchamp/pongchamp/Sounds/untitled4.mp3";
        clip = new AudioClip(new File(path).toURI().toString());
        clip.play();


    }

}
/*
 public void soundOnOff(ActionEvent event) {
        isSound = !isSound;
        if (isSound) {
            ef1();
        } else {
            ef2();

            sound.setOnAction(e -> {
                if (sound.isPressed()) {
                    med.pause();
                }else {
                    mediaPlayer.play();
                }
            });
        }
 @FXML
    public void clip() {
        obj.clip();
    }

    sound.setOnAction(e -> {
            if (sound.isSelected()) {
               clip.stop();
            } else {
                clip.play();
            }
        });
 */