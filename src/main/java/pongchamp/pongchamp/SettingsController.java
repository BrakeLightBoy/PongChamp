package pongchamp.pongchamp;

import javafx.animation.TranslateTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import pongchamp.pongchamp.FX.SoundEffects;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.view.SimpleRenderEngine;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends StartController implements Initializable {
    SoundEffects obj = new SoundEffects();

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    AnchorPane mainPane;
    @FXML
    Pane slider;
    @FXML
    Slider volumeSlider;
    @FXML
    ToggleButton musicB;
    @FXML
    Slider brightnessSlider;
    @FXML
    ToggleButton brightnessB;
    @FXML
    ColorPicker colorPicker;
    @FXML
    Circle circle;
    @FXML
    ImageView paddlempty;
    @FXML
    ToggleButton sound;
    @FXML
    private ImageView img;
    @FXML
    private boolean isLightMode = true;
    @FXML
    private ToggleButton darklight;

    public void switchToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void playHit(ActionEvent event) throws IOException {
        String path = "/Users/umair/Desktop/merge test/pongchamp/src/main/resources/pongchamp/pongchamp/Sounds/The Pharcyde-Ya Mama.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(5);
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

    @FXML
    public void adjustBrightness(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
        ColorAdjust bright = new ColorAdjust();
        root.setEffect(bright);
        brightnessSlider.setMin(-1);
        brightnessSlider.setMax(1);
        brightnessSlider.setBlockIncrement(0.1);
        brightnessSlider.setValue(0);


        brightnessSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                bright.setBrightness(newValue.doubleValue());
            }

        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sliderr(slider, mainPane);


        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                circle.setFill(colorPicker.getValue());
            }
        });

    }


    public void sliderr(Pane slider, AnchorPane mainPane) {

        slider.setOnMouseEntered(mouseEvent -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.2));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-199);

        });

        mainPane.setOnMouseMoved(mouseEvent -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.2));
            slide.setNode(slider);

            slide.setToX(-200);
            slide.play();


        });
    }

    @FXML
    private void handleColourPicker(ActionEvent event) {

    }




    public void play() {
        String path = "src/main/resources/pongchamp/pongchamp/Sounds/Celebration by Kool and the Gang with.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

    }
    public void changeMode(ActionEvent event) {
        isLightMode = !isLightMode;
        if (darklight.isPressed()) {
            setLightMode();
        } else {
            setDarkMode();
        }
    }

    private void setLightMode() {
        mainPane.getStylesheets().remove(SettingsController.class.getResource("dark.css").toString());
        mainPane.getStylesheets().add(SettingsController.class.getResource("light.css").toExternalForm());
        Image image = new Image(SettingsController.class.getResource("sunfull.PNG").toExternalForm());
        img.setImage(image);

    }

    private void setDarkMode() {
      mainPane.getStylesheets().remove(SettingsController.class.getResource("light.css").toExternalForm());
        mainPane.getStylesheets().add(SettingsController.class.getResource("dark.css").toExternalForm());
        Image image = new Image(SettingsController.class.getResource("sunempty.PNG").toExternalForm());
        img.setImage(image);
    }
}
/*
    protected void changeResolution() throws IOException {
        preferredWidth = 1920;
        preferredHeight = 1080;
        Stage stage = (Stage) changeResButton.getScene().getWindow();
        scene = new Scene(FXMLLoader.load(getClass().getResource("sliderHD.fxml")));
        stage.setWidth(preferredWidth);
        stage.setHeight(preferredHeight);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
        
 */
