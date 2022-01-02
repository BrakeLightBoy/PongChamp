package pongchamp.pongchamp.controller.json;

import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.GameModes;
import pongchamp.pongchamp.model.UserSettings;

import java.io.IOException;

public class JsonAPI {
    public void saveGame(Board board) throws IOException{ //this method is called when the user chooses to save the game after trying to quit a match that has power ups disabled (we don't save games that has power ups enabled)

    }
    public Board loadGame(){ // this method is called when the user clicks on the resume button. resume button needs to be created by the front team. the resume button is only available to click if the savedGameDetected method returns true
        return new Board(GameModes.END,false);
    }
    public boolean savedGameDetected(){ // this method checks if there is saved game inside a json file
        return false;
    }
    public UserSettings loadSettings(){ //this will load the user settings from a json file
        return new UserSettings();
    }
    public void saveSettings(UserSettings userSettings) throws IOException {

    }
}
