package pongchamp.pongchamp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pongchamp.pongchamp.model.GameModes;
import pongchamp.pongchamp.model.Properties;
import pongchamp.pongchamp.util.FrontendMethods;

import java.io.IOException;

import static pongchamp.pongchamp.util.FrontendMethods.createButton;


public class MainController {
    @FXML
    BorderPane bp;

    @FXML
    Stage stage;

    @FXML
    RadioButton PowerUpTgl, PowerUpTgl2;

    public void startMode() {
        loadPage("Main");
    }

    public void gameMode(MouseEvent mouseEvent) {
        loadPage("GameModeSelection");
    }

    public void Start1v1Local(MouseEvent mouseEvent) {
        startGameMode(stage, GameModes.V_1,PowerUpTgl.isSelected());
    }

    public void Start1v1AI(MouseEvent mouseEvent) {
        startGameMode(stage, GameModes.V_AI,PowerUpTgl.isSelected());
    }

    public void StartEndless(MouseEvent mouseEvent) {
        startGameMode(stage, GameModes.END, PowerUpTgl2.isSelected());
    }

    public void settingsPage(MouseEvent mouseEvent) {
        loadPage("Settings");
    }

    public void playMode(MouseEvent mouseEvent) {
        loadPage("Play");
    }

    public void exitPage(ActionEvent event) {
        stage = (Stage) bp.getScene().getWindow();
        stage.close();
    }


    public void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(root);
    }

    private void startGameMode(Stage stage, GameModes gameMode,boolean hasPowerUps){
        stage = (Stage) bp.getScene().getWindow();
        stage.setTitle("PONG");
        GameRenderer gameRenderer = new GameRenderer(gameMode,hasPowerUps, this);
        gameRenderer.start(stage);
    }
}
