package pongchamp.pongchamp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pongchamp.pongchamp.view.SimpleRenderEngine;

import javax.swing.*;
import java.io.IOException;

public class HelloController {

    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane mainPane;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSettings(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Settings.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void switchToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Added for MVP version where play button takes you to Swing render engine.
    public void switchToGame(ActionEvent event) throws IOException {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PongChamp");

        SimpleRenderEngine renderEngine = new SimpleRenderEngine();
        window.add(renderEngine);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    public void logout(ActionEvent event) {
        stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

}