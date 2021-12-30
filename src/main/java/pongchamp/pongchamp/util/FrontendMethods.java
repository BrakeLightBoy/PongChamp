package pongchamp.pongchamp.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class FrontendMethods {

    public static Button createButton(String Id, String text, boolean isVisible, double[] position, EventHandler<ActionEvent> action){
        Button button = new Button();

        button.setId(Id);
        button.setText(text);
        button.setVisible(isVisible);
        button.setLayoutX(position[0]);
        button.setLayoutY(position[1]);
        button.setOnAction(action);

        return button;
    }

}
