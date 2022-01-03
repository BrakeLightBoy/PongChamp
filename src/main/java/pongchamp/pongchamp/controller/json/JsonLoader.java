package pongchamp.pongchamp.controller.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.BoardState;
import pongchamp.pongchamp.model.Collidable;
import pongchamp.pongchamp.model.UserSettings;

import java.io.IOException;
import java.nio.file.Paths;


public class JsonLoader {

    private final Gson gson = GsonUtil.getGson();

    public UserSettings loadUserSettings() throws IOException {
        String json = FileUtil.readFile(Paths.get("settings.json"));
        return gson.fromJson(json,UserSettings.class);
    }
    public Board loadBoard() throws IOException{
        String json = FileUtil.readFile(Paths.get("board.json"));
        BoardState boardState =  gson.fromJson(json,BoardState.class);
        return new Board(boardState);
    }
}
