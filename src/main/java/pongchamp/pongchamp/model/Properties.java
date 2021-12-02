package pongchamp.pongchamp.model;

public class Properties {
    public static final float BOARD_WIDTH = 1200;
    public static final float BOARD_HEIGHT = 900;

    //Friction system needs to be discussed further.
    public static final float FRICTION = 0.8f;

    public static final float PLATFORM_SPEED = 10;
    public static final float PADDLE_DISTANCE_FROM_THE_EDGE = 40;
    public static final int TICKS_PER_SECOND = 30; //TODO IS THIS REALLY THE BEST OPTION?
    public static final int FRAMES_PER_SECOND = 60; //TODO IS THIS REALLY THE BEST OPTION?
}
