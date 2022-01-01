package pongchamp.pongchamp.model;

import javafx.scene.paint.Color;

public class UserSettings {
    private Color ballColour,paddle1Color,paddle2Color;

    public UserSettings(){
        //implement reading from JSON
        ballColour = Color.WHITE;
        paddle1Color = Color.WHITE;
        paddle2Color = paddle1Color;
    }

    public Color getBallColour(){
        return ballColour;
    }

    public Color getPaddle1Color () {
        return paddle1Color;
    }

    public Color getPaddle2Color(){
        return paddle2Color;
    }

    public void setBallColor(Color ballColour){
        this.ballColour = ballColour;
    }

    public void setPaddle1Color(Color paddle1Color) {
        this.paddle1Color = paddle1Color;
    }
    public void setPaddle2Color(Color paddle2Color) {
        this.paddle2Color = paddle2Color;
    }

}
