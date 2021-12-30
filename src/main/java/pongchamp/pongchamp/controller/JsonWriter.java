package pongchamp.pongchamp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.Collidable;
import pongchamp.pongchamp.model.OpponentType;
import pongchamp.pongchamp.model.UserSettings;
import pongchamp.pongchamp.model.entities.Paddle;

public class JsonWriter {





    public String writeSettings(UserSettings userSettings){
        Gson gson = new Gson();
        return gson.toJson(userSettings);
    }
    public String writeBoardState(Board board){

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Collidable.class, new CollidableSerializer());

        Gson gson = builder.create();
        return gson.toJson(board);
    }

}
