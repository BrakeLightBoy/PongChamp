package pongchamp.pongchamp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.Collidable;
import pongchamp.pongchamp.model.UserSettings;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonLoader {

    public JsonLoader ()  {

    }
    public UserSettings loadUserSettings(String json){
        Gson gson = new Gson();
       return gson.fromJson(json,UserSettings.class);
    }
    public Board loadBoard(String json){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Collidable.class, new CollidableDeserializer());
        Gson gson = builder.create();
        return gson.fromJson(json,Board.class);
    }
}
