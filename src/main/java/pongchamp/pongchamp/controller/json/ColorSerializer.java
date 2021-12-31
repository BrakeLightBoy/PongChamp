package pongchamp.pongchamp.controller.json;

import com.google.gson.*;
import javafx.scene.paint.Color;

import java.lang.reflect.Type;

public class ColorSerializer implements JsonSerializer<Color> {
    @Override
    public JsonElement serialize(Color color, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonElement jsonElement = JsonParser.parseString(color.toString());
        return jsonElement;

    }
}