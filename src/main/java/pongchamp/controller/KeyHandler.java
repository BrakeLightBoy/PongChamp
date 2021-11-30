package pongchamp.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener,PaddleController {

    private int upButton,downButton;

    private boolean upPressed, downPressed;

    public KeyHandler(int upButton, int downButton) {
        this.upButton = upButton;
        this.downButton = downButton;
    }

    public void keyTyped(KeyEvent e) {

    }


    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();


        if (code == upButton){
            upPressed = true;
        }
        else if (code == downButton){
            downPressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == upButton){
            upPressed = false;
        }
        else if (code == downButton){
            downPressed = false;
        }
    }

    @Override
    public boolean movingUp() {
        return upPressed;
    }

    @Override
    public boolean movingDown() {
        return downPressed;
    }
}
