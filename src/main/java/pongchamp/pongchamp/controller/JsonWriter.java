package pongchamp.pongchamp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.Collidable;
import pongchamp.pongchamp.model.OpponentType;
import pongchamp.pongchamp.model.UserSettings;
import pongchamp.pongchamp.model.entities.Paddle;

public class JsonWriter {

    private Gson gson;

    public JsonWriter(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Color.class, new ColorSerializer());
        builder.registerTypeAdapter(Collidable.class, new CollidableSerializer());
        gson = builder.create();
    }





    public String writeSettings(UserSettings userSettings){

        return gson.toJson(userSettings);
    }
    public String writeBoardState(Board board){

        return gson.toJson(board);
    }

    public static void main(String[] args) {
        JsonWriter writer = new JsonWriter();
        System.out.println(writer.writeSettings(new UserSettings()));
    }

}
