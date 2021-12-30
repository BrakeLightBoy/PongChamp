package pongchamp.pongchamp.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.Collidable;
import pongchamp.pongchamp.model.OpponentType;
import pongchamp.pongchamp.model.UserSettings;
import pongchamp.pongchamp.model.entities.Paddle;

public class JsonWriter {





    public String writeSettings(UserSettings userSettings){
        Gson gson = new Gson();
        return gson.toJson(userSettings);
    }
    public String writeBoardState(Board board){

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Collidable.class, new CollidableSerializer());

        Gson gson = builder.create();
        return gson.toJson(board);
    }

    public static void main(String[] args) throws Exception { //this is just for testing stuff


        JsonWriter writer = new JsonWriter();
        writer.writeBoardState(new Board(OpponentType.BEATABLE_AI_PADDLE,true));
        String text = "{\"settings\":{\"ballColor\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"paddle1Color\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"paddle2Color\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"backgroundColor\":{\"red\":0.0,\"green\":0.0,\"blue\":0.0,\"opacity\":1.0}},\"backgroundColor\":{\"red\":0.0,\"green\":0.0,\"blue\":0.0,\"opacity\":1.0},\"width\":1200.0,\"height\":700.0,\"paddleDistanceFromTheEdge\":40.0,\"hasEnded\":false,\"isPaused\":false,\"upperWall\":{\"wallType\":\"UPPER\",\"wallLine\":{\"startPoint\":{\"x\":0.0,\"y\":700.0},\"endPoint\":{\"x\":1200.0,\"y\":700.0}}},\"lowerWall\":{\"wallType\":\"LOWER\",\"wallLine\":{\"startPoint\":{\"x\":0.0,\"y\":0.0},\"endPoint\":{\"x\":1200.0,\"y\":0.0}}},\"leftPaddleMovementPath\":{\"startPoint\":{\"x\":40.0,\"y\":0.0},\"endPoint\":{\"x\":40.0,\"y\":700.0}},\"rightPaddleMovementPath\":{\"startPoint\":{\"x\":1160.0,\"y\":0.0},\"endPoint\":{\"x\":1160.0,\"y\":700.0}},\"leftPaddle\":{\"platformSpeed\":10.0,\"movementPath\":{\"startPoint\":{\"x\":40.0,\"y\":0.0},\"endPoint\":{\"x\":40.0,\"y\":700.0}},\"width\":20.0,\"height\":100.0,\"paddleHitBox\":{\"minX\":30.0,\"minY\":400.0,\"maxX\":50.0,\"maxY\":500.0,\"width\":20.0,\"height\":100.0,\"center\":{\"x\":40.0,\"y\":450.0}},\"paddleType\":\"LEFT\",\"paddleColor\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"uuid\":\"7eea5683-38f8-4866-8edc-e14be84a7ee5\",\"location\":{\"x\":40.0,\"y\":450.0},\"metadata\":{\"map\":{}}},\"rightPaddle\":{\"tick\":0,\"notMovedLastTick\":false,\"nextTickToMove\":0,\"lastYs\":[0.0,0.0,0.0],\"target\":{\"radius\":10,\"speed\":{\"x\":4.0,\"y\":8.0},\"acceleration\":{\"x\":0.0,\"y\":0.0},\"isVisible\":true,\"ballColor\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"uuid\":\"b5c43862-f9f9-45c6-9463-4b9a9b5305d7\",\"location\":{\"x\":600.0,\"y\":350.0},\"metadata\":{\"map\":{}}},\"platformSpeed\":10.0,\"movementPath\":{\"startPoint\":{\"x\":1160.0,\"y\":0.0},\"endPoint\":{\"x\":1160.0,\"y\":700.0}},\"width\":20.0,\"height\":100.0,\"paddleHitBox\":{\"minX\":1150.0,\"minY\":400.0,\"maxX\":1170.0,\"maxY\":500.0,\"width\":20.0,\"height\":100.0,\"center\":{\"x\":1160.0,\"y\":450.0}},\"paddleType\":\"RIGHT\",\"paddleColor\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"uuid\":\"997a1b61-b613-4823-9017-a73615d15ec3\",\"location\":{\"x\":1160.0,\"y\":450.0},\"metadata\":{\"map\":{}}},\"gameWinner\":\"Ongoing\",\"ball\":{\"radius\":10,\"speed\":{\"x\":4.0,\"y\":8.0},\"acceleration\":{\"x\":0.0,\"y\":0.0},\"isVisible\":true,\"ballColor\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"uuid\":\"b5c43862-f9f9-45c6-9463-4b9a9b5305d7\",\"location\":{\"x\":600.0,\"y\":350.0},\"metadata\":{\"map\":{}}},\"gameEntities\":[{\"platformSpeed\":10.0,\"movementPath\":{\"startPoint\":{\"x\":40.0,\"y\":0.0},\"endPoint\":{\"x\":40.0,\"y\":700.0}},\"width\":20.0,\"height\":100.0,\"paddleHitBox\":{\"minX\":30.0,\"minY\":400.0,\"maxX\":50.0,\"maxY\":500.0,\"width\":20.0,\"height\":100.0,\"center\":{\"x\":40.0,\"y\":450.0}},\"paddleType\":\"LEFT\",\"paddleColor\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"uuid\":\"7eea5683-38f8-4866-8edc-e14be84a7ee5\",\"location\":{\"x\":40.0,\"y\":450.0},\"metadata\":{\"map\":{}}},{\"tick\":0,\"notMovedLastTick\":false,\"nextTickToMove\":0,\"lastYs\":[0.0,0.0,0.0],\"target\":{\"radius\":10,\"speed\":{\"x\":4.0,\"y\":8.0},\"acceleration\":{\"x\":0.0,\"y\":0.0},\"isVisible\":true,\"ballColor\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"uuid\":\"b5c43862-f9f9-45c6-9463-4b9a9b5305d7\",\"location\":{\"x\":600.0,\"y\":350.0},\"metadata\":{\"map\":{}}},\"platformSpeed\":10.0,\"movementPath\":{\"startPoint\":{\"x\":1160.0,\"y\":0.0},\"endPoint\":{\"x\":1160.0,\"y\":700.0}},\"width\":20.0,\"height\":100.0,\"paddleHitBox\":{\"minX\":1150.0,\"minY\":400.0,\"maxX\":1170.0,\"maxY\":500.0,\"width\":20.0,\"height\":100.0,\"center\":{\"x\":1160.0,\"y\":450.0}},\"paddleType\":\"RIGHT\",\"paddleColor\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"uuid\":\"997a1b61-b613-4823-9017-a73615d15ec3\",\"location\":{\"x\":1160.0,\"y\":450.0},\"metadata\":{\"map\":{}}}],\"obstacles\":[{\"platformSpeed\":10.0,\"movementPath\":{\"startPoint\":{\"x\":40.0,\"y\":0.0},\"endPoint\":{\"x\":40.0,\"y\":700.0}},\"width\":20.0,\"height\":100.0,\"paddleHitBox\":{\"minX\":30.0,\"minY\":400.0,\"maxX\":50.0,\"maxY\":500.0,\"width\":20.0,\"height\":100.0,\"center\":{\"x\":40.0,\"y\":450.0}},\"paddleType\":\"LEFT\",\"paddleColor\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"uuid\":\"7eea5683-38f8-4866-8edc-e14be84a7ee5\",\"location\":{\"x\":40.0,\"y\":450.0},\"metadata\":{\"map\":{}},\"className\":\"Paddle\"},{\"tick\":0,\"notMovedLastTick\":false,\"nextTickToMove\":0,\"lastYs\":[0.0,0.0,0.0],\"target\":{\"radius\":10,\"speed\":{\"x\":4.0,\"y\":8.0},\"acceleration\":{\"x\":0.0,\"y\":0.0},\"isVisible\":true,\"ballColor\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"uuid\":\"b5c43862-f9f9-45c6-9463-4b9a9b5305d7\",\"location\":{\"x\":600.0,\"y\":350.0},\"metadata\":{\"map\":{}}},\"platformSpeed\":10.0,\"movementPath\":{\"startPoint\":{\"x\":1160.0,\"y\":0.0},\"endPoint\":{\"x\":1160.0,\"y\":700.0}},\"width\":20.0,\"height\":100.0,\"paddleHitBox\":{\"minX\":1150.0,\"minY\":400.0,\"maxX\":1170.0,\"maxY\":500.0,\"width\":20.0,\"height\":100.0,\"center\":{\"x\":1160.0,\"y\":450.0}},\"paddleType\":\"RIGHT\",\"paddleColor\":{\"red\":1.0,\"green\":1.0,\"blue\":1.0,\"opacity\":1.0},\"uuid\":\"997a1b61-b613-4823-9017-a73615d15ec3\",\"location\":{\"x\":1160.0,\"y\":450.0},\"metadata\":{\"map\":{}},\"className\":\"MediumAIPaddle\"},{\"wallType\":\"LOWER\",\"wallLine\":{\"startPoint\":{\"x\":0.0,\"y\":0.0},\"endPoint\":{\"x\":1200.0,\"y\":0.0}},\"className\":\"Wall\"},{\"wallType\":\"UPPER\",\"wallLine\":{\"startPoint\":{\"x\":0.0,\"y\":700.0},\"endPoint\":{\"x\":1200.0,\"y\":700.0}},\"className\":\"Wall\"}],\"spawnedPowerUps\":[],\"activatedPowerUps\":[],\"maintainedPowerUps\":[],\"toRemove\":[],\"hasPowerUps\":true,\"leftScore\":0,\"rightScore\":0}\n";
        JsonLoader loader = new JsonLoader(text);
        System.out.println(loader.loadBoard());

    }
}
