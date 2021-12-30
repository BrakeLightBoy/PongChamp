package pongchamp.pongchamp.model;


import javafx.scene.paint.Color;

public class UserSettings {
    private Color ballColor,paddle1Color,paddle2Color,backgroundColor;

    //core func
    // board state
    // colors of ball, paddles, background

    //a bit extra
    // int of losses, wins and draws
    // unlocked/locked (boolean) game modes


    public UserSettings(){
        //read from JSON
        ballColor = Color.WHITE;
        paddle1Color = Color.WHITE;
        paddle2Color = Color.WHITE;
        backgroundColor = Color.BLACK;
    }

    public Color getBallColor() {
        return ballColor;
    }

    public Color getPaddle1Color() {
        return paddle1Color;
    }

    public Color getPaddle2Color() {
        return paddle2Color;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "ballColor=" + ballColor +
                ", paddle1Color=" + paddle1Color +
                ", paddle2Color=" + paddle2Color +
                ", backgroundColor=" + backgroundColor +
                '}';
    }
}
