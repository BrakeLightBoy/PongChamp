package pongchamp.pongchamp.controller.json;

import com.google.gson.Gson;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.BoardState;
import pongchamp.pongchamp.model.UserSettings;

public class JsonWriter {

    private final Gson gson = GsonUtil.getGson();

    public String writeSettings(UserSettings userSettings){
        return gson.toJson(userSettings);
    }
    public String writeBoardState(Board board){

        return gson.toJson(new BoardState(board));
    }


}
