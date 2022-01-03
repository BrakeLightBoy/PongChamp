package pongchamp.pongchamp.controller.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.Collidable;
import pongchamp.pongchamp.model.entities.Paddle;

public class GsonUtil {
    private static Gson gson;

    public static Gson getGson(){
        if (gson == null){
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(Color.class, new ColorSerializer());
            builder.registerTypeAdapter(Color.class, new ColorDeserializer());
            gson = builder.create();
        }
        return gson;
    }
}
