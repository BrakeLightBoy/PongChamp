package pongchamp.pongchamp.controller;

import com.google.gson.Gson;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.UserSettings;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonLoader {
    private String json;
    public JsonLoader (String path) throws IOException {
        this.json = Files.readString(Paths.get(path));
    }
    public UserSettings loadUserSettings(){
        Gson gson = new Gson();
       return gson.fromJson(json,UserSettings.class);
    }
    public Board loadBoard(){
        return null;
    }
}
