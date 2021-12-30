package pongchamp.pongchamp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.Collidable;
import pongchamp.pongchamp.model.UserSettings;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonLoader {

    private Gson gson;

    public JsonLoader ()  {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Color.class, new ColorDeserializer());
        builder.registerTypeAdapter(Collidable.class, new CollidableDeserializer());
        gson = builder.create();

    }
    public UserSettings loadUserSettings(String json){

       return gson.fromJson(json,UserSettings.class);
    }
    public Board loadBoard(String json){
        return gson.fromJson(json,Board.class);
    }
}
