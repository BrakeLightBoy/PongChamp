package pongchamp.pongchamp.controller.json;

import com.google.gson.Gson;
import pongchamp.pongchamp.model.Board;

import pongchamp.pongchamp.model.UserSettings;

public class JsonWriter {




    public JsonWriter(){

    }

    public void writeSettings(UserSettings userSettings){
        Gson gson = new Gson();
        String json = gson.toJson(userSettings);
        System.out.println(json); //todo write to actual file somewhere
    }
    public void writeBoardState(Board board){
        Gson gson = new Gson();
        String json = gson.toJson(board);
        System.out.println(json);
    }


}
