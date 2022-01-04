package pongchamp.pongchamp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pongchamp.pongchamp.controller.json.JsonAPI;
import pongchamp.pongchamp.model.UserSettings;

import java.io.File;
import java.io.IOException;


public class Application extends javafx.application.Application {
    UserSettings userSettings = new UserSettings();
    JsonAPI jsonAPI = new JsonAPI();

    @Override
    public void start(Stage stage) throws IOException {
        checkSettingsExist();

        Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("PONG!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

    private void checkSettingsExist(){
        File tempFile = new File("settings.json");
        if(tempFile.exists()){
            try {
                userSettings = jsonAPI.loadSettings();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                jsonAPI.saveSettings(userSettings);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

