package com.example.finalfrontier.controller.ai;

import com.example.finalfrontier.controller.PaddleController;
import com.example.finalfrontier.model.CollisionTypes;
import com.example.finalfrontier.model.Properties;
import com.example.finalfrontier.model.entities.Ball;
import com.example.finalfrontier.model.math.LineSegment;
import com.example.finalfrontier.model.math.Point;
import com.example.finalfrontier.model.math.Vector;

public class UnbeatableAIPaddle extends AIPaddle{




    private Point previousLocation;


    public UnbeatableAIPaddle(Point location, LineSegment movementPath, PaddleController paddleController, CollisionTypes paddleType, Ball target) {
        super(location,defaultPaddleWidth , defaultPaddleHeight, movementPath, paddleController, paddleType,target);
        setPaddleController(null);
    }


    @Override
    public void tick() {
        previousLocation = new Point(location.getX(), location.getY());
        float targetCord = target.getLocation().getY();
        if (targetCord < 0 + height / 2 || targetCord > Properties.BOARD_HEIGHT - height / 2)return;
        this.location.setY(target.getLocation().getY());
        Vector dif = new Vector(location.getX() -previousLocation.getX() ,location.getY()- previousLocation.getY());
        this.paddleHitBox.moveHitBox(dif);
    }


    @Override
    public boolean movingUp() {
        return false;
    }

    @Override
    public boolean movingDown() {
        return false;
    }
}
