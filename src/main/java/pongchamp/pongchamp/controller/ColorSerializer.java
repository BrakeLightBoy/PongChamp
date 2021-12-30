package pongchamp.pongchamp.controller;

import com.google.gson.*;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.model.Collidable;

import java.lang.reflect.Type;

public class ColorSerializer implements JsonSerializer<Color> {
    @Override
    public JsonElement serialize(Color color, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonElement jsonElement = JsonParser.parseString(color.toString());
        return jsonElement;

    }
}
