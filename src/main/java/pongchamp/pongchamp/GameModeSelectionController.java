package pongchamp.pongchamp;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import pongchamp.pongchamp.model.Board;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameModeSelectionController extends MainController implements Initializable {

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
    @Override
    public void Start1v1Local(MouseEvent event) {
        super.Start1v1Local(event);
    }

    @FXML
    AnchorPane InfoCard;

    @FXML
    AnchorPane InfoCard1;

    @FXML
    Button LoadSaveBtn;

    MainController mainController = new MainController();


    protected void animateIn(AnchorPane anchorPane){ //Animates the given anchor pane in
        anchorPane.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(130), anchorPane);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

    protected void animateOut(AnchorPane anchorPane){ //Animates the given anchor pane out
        FadeTransition ft = new FadeTransition(Duration.millis(130), anchorPane);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
        anchorPane.setVisible(false);
    }

    @FXML
    protected void onButton1v1ButtonClick() {
        onCloseWindow2Click(); //Close the other info card if it's open
        animateIn(InfoCard);
    }

    @FXML
    protected void onCloseWindowClick() {
        animateOut(InfoCard);
    }

    @FXML
    protected void onButtonEndlessButtonClick() {
        onCloseWindowClick(); //Close the other info card if it's open
        animateIn(InfoCard1);
    }

    @FXML
    protected void onCloseWindow2Click(){
        animateOut(InfoCard1);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadSaveBtn.setDisable(!jsonAPI.savedGameDetected());
    }

    @FXML
    protected void onLoadSaveBtnClick() throws IOException {
        try {
            Board savedBoard = jsonAPI.loadGame();
            GameRenderer newGameRender = new GameRenderer(savedBoard.getGameMode(), false, mainController, jsonAPI.loadGame());
            newGameRender.start((Stage) InfoCard.getScene().getWindow());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

