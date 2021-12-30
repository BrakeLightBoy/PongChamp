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
    private final String json;
    public JsonLoader (String json) throws IOException {
        this.json = json;
    }
    public UserSettings loadUserSettings(){
        Gson gson = new Gson();
       return gson.fromJson(json,UserSettings.class);
    }
    public Board loadBoard(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Collidable.class, new CollidableDeserializer());
        Gson gson = builder.create();
        return gson.fromJson(json,Board.class);
    }
}
