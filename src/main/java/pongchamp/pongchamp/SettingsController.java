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
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController extends StartController implements Initializable {

     private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    AnchorPane mainPane;
    @FXML
    Pane slider;
    @FXML
    Rectangle clip;
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
    Ellipse ellipse;

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
            }else {
                mediaPlayer.play();
            }
        });

    }
    @FXML
    public void adjustBrightness(ActionEvent event) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("slider.fxml"));
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
                ellipse.setFill(colorPicker.getValue());


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



}
