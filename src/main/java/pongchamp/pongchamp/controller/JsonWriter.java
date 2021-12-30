package pongchamp.pongchamp.controller;

import com.google.gson.Gson;
import pongchamp.pongchamp.model.Board;

import pongchamp.pongchamp.model.GameModes;
import pongchamp.pongchamp.model.UserSettings;

public class JsonWriter {



    private String pathToWrite;

    public JsonWriter(String pathToWrite){
        this.pathToWrite = pathToWrite;
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

    public static void main(String[] args) throws Exception { //this is just for testing stuff
        JsonWriter writer = new JsonWriter(null);
        writer.writeBoardState(new Board(GameModes.V_AI,true));


    }
}
