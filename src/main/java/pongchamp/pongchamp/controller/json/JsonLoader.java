package pongchamp.pongchamp.controller.json;

import com.google.gson.Gson;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.UserSettings;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonLoader {


    public UserSettings loadUserSettings(){
        Gson gson = new Gson();
        return null;
    }
    public Board loadBoard(String json){
        return null;
    }
}
