package pongchamp.pongchamp.controller.json;

import com.google.gson.*;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.Collidable;
import pongchamp.pongchamp.model.entities.Paddle;

import java.lang.reflect.Type;

public class PaddleSerializer implements JsonSerializer<Paddle> {
    @Override
    public JsonElement serialize(Paddle paddle, Type type, JsonSerializationContext jsonSerializationContext) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Color.class, new ColorSerializer());
        builder.registerTypeAdapter(Collidable.class, new CollidableSerializer());
        builder.registerTypeAdapter(Color.class, new ColorDeserializer());
        builder.registerTypeAdapter(Collidable.class, new CollidableDeserializer());
        builder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return aClass.isAssignableFrom(PaddleController.class);
            }
        });
        Gson gson = builder.create();
        String json = gson.toJson(paddle);
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        jsonObject.addProperty("className",paddle.getClass().getSimpleName());
        return JsonParser.parseString(jsonObject.toString());

    }
}
