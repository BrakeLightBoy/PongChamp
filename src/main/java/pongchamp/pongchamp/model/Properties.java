package pongchamp.pongchamp.model;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pongchamp.pongchamp.model.math.Vector;

public class Properties {
    public static final float BOARD_WIDTH = 1200;
    public static final float BOARD_HEIGHT = 700;

    public static final int BALL_RADIUS = 10;
    public static final int POWER_UP_RADIUS = 50;
    public static final Vector INITIAL_SPEED = new Vector(4,8);

    //Friction system needs to be discussed further.
    public static final float FRICTION = 0.8f;

    public static final float PLATFORM_SPEED = 10;
    public static final float PADDLE_DISTANCE_FROM_THE_EDGE = 40;
    public static final int TICKS_PER_SECOND = 30; //TODO IS THIS REALLY THE BEST OPTION?
    public static final int FRAMES_PER_SECOND = 60; //TODO IS THIS REALLY THE BEST OPTION?

    public static final int MAXNUMBEROFPOWERUPS = 3;

    public static final Color FONT_COLOR = Color.WHITE;
    public static final Font FONT_SIZE = Font.font(25);

}
