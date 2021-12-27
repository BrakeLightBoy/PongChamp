package com.example.finalfrontier.model;


import com.example.finalfrontier.model.math.LineSegment;
import com.example.finalfrontier.model.math.LineSegment;

import static com.example.finalfrontier.model.CollisionTypes.*;
import static com.example.finalfrontier.model.CollisionTypes.LEFT;
import static com.example.finalfrontier.model.CollisionTypes.LOWER;
import static com.example.finalfrontier.model.CollisionTypes.RIGHT;
import static com.example.finalfrontier.model.CollisionTypes.UPPER;
import static com.example.finalfrontier.model.ObstactleTypes.*;

public class Wall implements Collidable {
    private final CollisionTypes wallType;
    private final LineSegment wallLine;



    public Wall(CollisionTypes wallType, LineSegment wallLine) {

        if (! (wallType == RIGHT || wallType == LEFT || wallType == UPPER || wallType == LOWER) ){
                throw new IllegalArgumentException("invalid wall type");
        }
        this.wallType = wallType;
        this.wallLine = wallLine;
    }

    public CollisionTypes getWallType() {
        return wallType;
    }

    public LineSegment getWallLine() {
        return wallLine;
    }
}
