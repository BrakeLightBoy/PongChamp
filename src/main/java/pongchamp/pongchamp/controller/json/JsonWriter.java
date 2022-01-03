package pongchamp.pongchamp.controller.json;

import com.google.gson.Gson;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.BoardState;
import pongchamp.pongchamp.model.UserSettings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonWriter {

    private final Gson gson = GsonUtil.getGson();

    public void writeSettings(UserSettings userSettings) throws IOException{
        String json = gson.toJson(userSettings);
        FileUtil.writeToFile(Paths.get("settings.json"),json);
    }
    public void writeBoardState(Board board) throws IOException {
        String json = gson.toJson(new BoardState(board));
        FileUtil.writeToFile(Paths.get("board.json"),json);
    }



}
