package pongchamp.pongchamp.model.entities;


import javafx.scene.control.ColorPicker;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import pongchamp.pongchamp.FX.SoundEffects;
import pongchamp.pongchamp.controller.PaddleController;
import pongchamp.pongchamp.model.Board;
import pongchamp.pongchamp.model.Collidable;
import pongchamp.pongchamp.model.Collision;
import pongchamp.pongchamp.model.HitBox;
import pongchamp.pongchamp.model.math.LineSegment;
import pongchamp.pongchamp.model.math.Point;

import java.io.File;

public abstract class Paddle extends Entity implements Collidable {
    protected LineSegment movementPath;
    protected PaddleController paddleController;
    protected float width;
    protected float height;
    protected HitBox paddleHitBox;
    protected String paddleType;

    public Paddle(Board board, Point location, float width, float height , LineSegment movementPath, PaddleController paddleController, String paddleType) {
        super(board,location);
        if (!(paddleType.equals("left")||paddleType.equals("right"))){
            throw new IllegalArgumentException("Wrong paddle type");
        }
        this.paddleType = paddleType;
        this.movementPath = movementPath;
        this.paddleController = paddleController;
        this.width =  width;
        this.height = height;
        paddleHitBox = new HitBox(location.getX()-width/2f,location.getY()-height/2f, location.getX()+width/2f, location.getY()+height/2f);

    }
    public void testBallSound() {
        String path = "/Users/umair/Desktop/Untitled3.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(1);
        mediaPlayer.play();

    }

    public abstract Collision checkBallCollision(Ball ball);

    public void setPaddleController(PaddleController paddleController) {
        this.paddleController = paddleController;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
