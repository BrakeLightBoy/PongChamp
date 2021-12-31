package pongchamp.pongchamp.controller.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.BoardState;
import pongchamp.pongchamp.model.Collidable;
import pongchamp.pongchamp.model.UserSettings;


public class JsonLoader {

    private final Gson gson = GsonUtil.getGson();

    public UserSettings loadUserSettings(String json){
       return gson.fromJson(json,UserSettings.class);
    }
    public Board loadBoard(String json){
        BoardState boardState =  gson.fromJson(json,BoardState.class);
        return new Board(boardState);
    }
}
