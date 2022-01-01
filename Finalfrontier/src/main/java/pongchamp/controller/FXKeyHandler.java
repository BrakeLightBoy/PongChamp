package pongchamp.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FXKeyHandler implements PaddleController, EventHandler<KeyEvent> {

    private final KeyCode upButton,downButton;

    private boolean upPressed, downPressed;

    public FXKeyHandler(KeyCode upButton, KeyCode downButton){
        this.upButton = upButton;
        this.downButton = downButton;
    }

    @Override
    public boolean movingDown() {
        return downPressed;
    }

    @Override
    public boolean movingUp() {
        return upPressed;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED){
            if (keyEvent.getCode() == upButton){
                upPressed = true;
            }
            else if (keyEvent.getCode() == downButton){
                downPressed = true;
            }
        }
        else if (keyEvent.getEventType() == KeyEvent.KEY_RELEASED){
            if (keyEvent.getCode() == upButton){
                upPressed = false;
            }
            else if (keyEvent.getCode() == downButton){
                downPressed = false;
            }
        }
    }
}
