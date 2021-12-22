package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {


    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ToggleButton butt33o1n;

    @FXML
    private ImageView img;

    @FXML
    private AnchorPane parent;
    @FXML
    private Button button;
    @FXML
    private boolean isLightMode = true;
    Toggle obj = new Toggle();

    public void changeMode(ActionEvent event) {
        isLightMode = !isLightMode;
        if (isLightMode) {
            setLightMode();
        } else {
            setDarkMode();
        }
    }
    public void switchToSettings(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("test1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void setLightMode() {
        parent.getStylesheets().remove(HelloController.class.getResource("dark.css").toExternalForm());
        parent.getStylesheets().add(HelloController.class.getResource("light.css").toExternalForm());
        Image image = new Image(HelloController.class.getResource("sunfull.PNG").toExternalForm());
        img.setImage(image);

    }

    private void setDarkMode() {
        parent.getStylesheets().remove(HelloController.class.getResource("light.css").toExternalForm());
        parent.getStylesheets().add(HelloController.class.getResource("dark.css").toExternalForm());
        Image image = new Image(HelloController.class.getResource("sunempty.PNG").toExternalForm());
        img.setImage(image);
    }

}
/*
    public void initialize(URL url, ResourceBundle rb) {
        ToggleSwitch button = new ToggleSwitch();
        BooleanProperty isOn = obj.switchOnProperty();
        isOn.addListener((observableValue, oldValue, newValue) -> {

            if (newValue) {
                button.getScene().getRoot().getStylesheets().add(getClass().getResource("dark.css").toString());
            } else {
                button.getScene().getRoot().getStylesheets().remove(getClass().getResource("light.css").toString());

            }
        });
        parent.getChildren().add(button);
    }
    }


 */


