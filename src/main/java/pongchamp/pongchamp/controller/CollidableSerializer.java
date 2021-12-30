package pongchamp.pongchamp.controller;

import com.google.gson.*;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.Collidable;

import java.lang.reflect.Type;

public class CollidableSerializer implements JsonSerializer<Collidable> {
    @Override
    public JsonElement serialize(Collidable collidable, Type type, JsonSerializationContext jsonSerializationContext) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Color.class, new ColorSerializer());
        Gson gson = builder.create();
        String json = gson.toJson(collidable);
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        jsonObject.addProperty("className",collidable.getClass().getSimpleName());
        return JsonParser.parseString(jsonObject.toString());
    }
}
