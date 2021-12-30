package pongchamp.pongchamp.controller;


import com.google.gson.*;
import javafx.scene.paint.Color;
import pongchamp.pongchamp.controller.ai.MediumAIPaddle;
import pongchamp.pongchamp.controller.ai.UnbeatableAIPaddle;
import pongchamp.pongchamp.model.Collidable;
import pongchamp.pongchamp.model.Wall;
import pongchamp.pongchamp.model.entities.Paddle;

import java.lang.reflect.Type;

public class CollidableDeserializer implements JsonDeserializer<Collidable> {


    @Override
    public Collidable deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String className = jsonObject.get("className").getAsString();
        jsonObject.remove("className");
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Color.class, new ColorDeserializer());
        Gson gson = builder.create();
        switch (className){
            case "Paddle" -> {
               return gson.fromJson(jsonObject.toString(), Paddle.class);
            }
            case "MediumAIPaddle" -> {
                return gson.fromJson(jsonObject.toString(), MediumAIPaddle.class);
            }
            case "UnbeatableAIPaddle" -> {
                return gson.fromJson(jsonObject.toString(), UnbeatableAIPaddle.class);
            }
            case "Wall" -> {
                return gson.fromJson(jsonObject.toString(), Wall.class);
            }
        }
        return null;
    }
}
