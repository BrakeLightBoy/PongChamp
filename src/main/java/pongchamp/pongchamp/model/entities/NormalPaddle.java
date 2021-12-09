package pongchamp.pongchamp.model.entities;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import pongchamp.pongchamp.FX.SoundEffects;
import pongchamp.pongchamp.SettingsController;
import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.*;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;
import pongchamp.pongchamp.model.math.Vector;

import java.io.File;

public class NormalPaddle extends Paddle {

    private static final float platformSpeed = Properties.PLATFORM_SPEED;
    private static final int defaultPaddleWidth = 20; //todo consider whether these sizes are good sizes in a 1200x900 board or not
    private static final int defaultPaddleHeight = 100;
    SoundEffects obj = new SoundEffects();
    SettingsController obj1 = new SettingsController();

    public NormalPaddle(Board board, Point initialLocation, LineSegment movementPath, PaddleController paddleController, String paddleType) { //if you don't put a height and width while making a paddle, the default sizes will be used
        this(board, initialLocation, defaultPaddleWidth, defaultPaddleHeight, movementPath, paddleController, paddleType);
    }

    public NormalPaddle(Board board, Point initialLocation, float width, float height, LineSegment movementPath, PaddleController paddleController, String paddleType) {
        super(board, initialLocation, width, height, movementPath, paddleController, paddleType);
    }

    @Override
    public void tick() {

        if (paddleController.movingDown()) {
            Vector movementVector = new Vector(0, platformSpeed);
            location.movePoint(movementVector);
            paddleHitBox.moveHitBox(movementVector);

        } else if (paddleController.movingUp()) {
            Vector movementVector = new Vector(0, -platformSpeed);
            location.movePoint(movementVector);
            paddleHitBox.moveHitBox(movementVector);

        }
    }

    @Override
    public Collision checkBallCollision(Ball ball) {
        String collidedPart = paddleHitBox.checkBallIntersect(ball);

        if (collidedPart == null) {
            return null;
        } else {
            String data = "paddle-";
            clip();
            if (paddleType.equals("left")) {
                data += "left";
            } else {
                data += "right";
            }

            if (collidedPart.equals("vertical")) {
                data += "-longSegment";
            } else {
                //FLIPPED DUE TO Y-AXIS RENDERING
                if (ball.location.getY() < location.getY()) {
                    data += "-higher";
                } else {
                    data += "-lower";
                }

                if (collidedPart.equals("horizontal")) {
                    data += "-shortSegment";
                } else {
                    data += "-corner";
                }
            }


            return new Collision(data);
        }
    }

    public void tbs1() {
        obj.ef1();
    }
    public void tbs2() {
        obj.ef2();
    }
    public void clip() {
        obj.clip();
    }
}
