package pongchamp.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener,PaddleController {

    private boolean upPressed, downPressed;


    public void keyTyped(KeyEvent e) {

    }


    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();


        if (code == KeyEvent.VK_W){
            upPressed = true;
        }
        else if (code == KeyEvent.VK_S){
            downPressed = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W){
            upPressed = false;
        }
        else if (code == KeyEvent.VK_S){
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
