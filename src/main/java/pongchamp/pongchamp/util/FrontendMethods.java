package pongchamp.pongchamp.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;

public class FrontendMethods {

    public static Button createButton(String Id, String text, boolean isVisible, double[] position, double scale, EventHandler<ActionEvent> action){
        Button button = new Button();

        button.setId(Id);
        button.setText(text);
        button.setVisible(isVisible);
        button.setLayoutX(position[0]);
        button.setLayoutY(position[1]);
        button.setOnAction(action);
        button.setScaleX(scale);
        button.setScaleY(scale);

        return button;
    }
    
    public static RadioButton createRadioButton(String Id, String text, boolean isVisible, double[] position){
        RadioButton radioButton = new RadioButton();

        radioButton.setId(Id);
        radioButton.setText(text);
        radioButton.setVisible(isVisible);
        radioButton.setLayoutX(position[0]);
        radioButton.setLayoutY(position[1]);

        return radioButton;
    }

}
