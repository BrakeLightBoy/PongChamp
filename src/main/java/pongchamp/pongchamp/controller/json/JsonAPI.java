package pongchamp.pongchamp.controller.json;

import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.GameModes;
import pongchamp.pongchamp.model.UserSettings;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonAPI {
    public void saveGame(Board board) throws IOException{ //this method is called when the user chooses to save the game after trying to quit a match that has power ups disabled (we don't save games that has power ups enabled)
        JsonWriter writer = new JsonWriter();
        writer.writeBoardState(board);
    }
    public Board loadGame() throws IOException{ // this method is called when the user clicks on the resume button. resume button needs to be created by the front team. the resume button is only available to click if the savedGameDetected method returns true
        JsonLoader loader = new JsonLoader();
        return loader.loadBoard();
    }
    public boolean savedGameDetected(){ // this method checks if there is saved game inside a json file
        Path path = Paths.get("board.json");
        return path.toFile().exists();
    }
    public UserSettings loadSettings() throws IOException{ //this will load the user settings from a json file
        JsonLoader loader = new JsonLoader();
        return loader.loadUserSettings();
    }
    public void saveSettings(UserSettings userSettings) throws IOException {
        JsonWriter writer = new JsonWriter();
        writer.writeSettings(userSettings);
    }
}
