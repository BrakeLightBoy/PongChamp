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

    Stage stage;


    public void startMode() {
        Stage stage = new Stage();
        stage.setTitle("PONG");

        double[] gameMode1 = {(double) Properties.BOARD_WIDTH*0.40,(double) Properties.BOARD_HEIGHT*0.52};
        double[] gameMode2 = {(double) Properties.BOARD_WIDTH*0.50,(double) Properties.BOARD_HEIGHT*0.52};
        double[] gameMode3 = {(double) Properties.BOARD_WIDTH*0.55,(double) Properties.BOARD_HEIGHT*0.52};
        double[] powerUpTgl = {(double) Properties.BOARD_WIDTH*0.50,(double) Properties.BOARD_HEIGHT*0.40};




        RadioButton regularToggle = new RadioButton();
        regularToggle.setText("Regular");

        RadioButton powerUpToggle = FrontendMethods.createRadioButton("powerUpTgl", "With Power ups", true, powerUpTgl);

        Button gameMode1Btn = createButton("btn1", "vs Player", true, gameMode1, e-> {
            startGameMode(stage, GameModes.V_1,powerUpToggle.isSelected());
        });
        Button gameMode2Btn = createButton("btn2", "vs AI", true, gameMode2, e-> {
            startGameMode(stage, GameModes.V_AI,powerUpToggle.isSelected());
        });
        Button gameMode3Btn = createButton("btn3", "Endless", true, gameMode3, e-> {
            startGameMode(stage, GameModes.END,powerUpToggle.isSelected());
        });


        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(gameMode1Btn, gameMode2Btn, gameMode3Btn, powerUpToggle);

        Scene scene = new Scene(anchorPane, Properties.BOARD_WIDTH, Properties.BOARD_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void gameMode(MouseEvent mouseEvent) {
        loadPage("GameModeSelection");
    }

    public void Start1v1Local(MouseEvent mouseEvent) {
        loadPage("Game");
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
        GameRenderer gameRenderer = new GameRenderer(gameMode,hasPowerUps, this);
        gameRenderer.start(stage);
    }
}
