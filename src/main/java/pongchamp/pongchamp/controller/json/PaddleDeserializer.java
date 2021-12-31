package pongchamp.pongchamp.controller.json;

import com.google.gson.*;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.controller.ai.MediumAIPaddle;
import pongchamp.pongchamp.controller.ai.UnbeatableAIPaddle;
import pongchamp.pongchamp.model.Collidable;
import pongchamp.pongchamp.model.entities.Paddle;

import java.lang.reflect.Type;

public class PaddleDeserializer implements JsonDeserializer<Paddle> {
    @Override
    public Paddle deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String className = jsonObject.get("className").getAsString();
        jsonObject.remove("className");
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
        switch (className) {
            case "Paddle" -> {
                return gson.fromJson(jsonObject.toString(), Paddle.class);
            }
            case "MediumAIPaddle" -> {
                MediumAIPaddle mediumAIPaddle = gson.fromJson(jsonObject.toString(), MediumAIPaddle.class);
                mediumAIPaddle.setPaddleController(mediumAIPaddle);
                return mediumAIPaddle;
            }
            case "UnbeatableAIPaddle" -> {
                return gson.fromJson(jsonObject.toString(), UnbeatableAIPaddle.class);
            }
        }
        return null;
    }
}
