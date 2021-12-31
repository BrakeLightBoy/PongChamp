package pongchamp.pongchamp.controller.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

    public void writeToFile(Path path,String content) throws IOException{
        Files.writeString(path, content);
    }

    public String readFile(Path path) throws IOException{
        return Files.readString(path);
    }

}
