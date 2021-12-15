package pongchamp.pongchamp.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;


public class InGameKeyListener implements EventHandler<KeyEvent> {
    private List<EventHandler<KeyEvent>> keyListeners = new ArrayList<>();
    @Override
    public void handle(KeyEvent keyEvent) {
        for (EventHandler<KeyEvent> keyListener : keyListeners) {
            keyListener.handle(keyEvent);
        }
    }

    public void registerKeyListener(EventHandler<KeyEvent> eventHandler){
        keyListeners.add(eventHandler);
    }
}
