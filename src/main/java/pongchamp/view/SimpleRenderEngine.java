package pongchamp.view;

import pongchamp.controller.KeyHandler;
import pongchamp.model.Board;
import pongchamp.model.entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class SimpleRenderEngine extends JPanel implements Runnable {
    //this will be removed in the production. this is just for illustration purposes
    //the simple render engine has the exact same size as the simulation board, but that's not always the cae in production


    private final int screenWidth = 1200;
    private final int screenHeight = 900;

    KeyHandler keyHandlerLeft = new KeyHandler(KeyEvent.VK_W,KeyEvent.VK_S);
    KeyHandler keyHandlerRight = new KeyHandler(KeyEvent.VK_UP,KeyEvent.VK_DOWN);

    Board board;

    Thread gameThread;

    public SimpleRenderEngine(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandlerLeft);
        this.addKeyListener(keyHandlerRight);
        this.setFocusable(true);
        board = new Board();
        startGameThread();
        board.getLeftPaddle().setPaddleController(keyHandlerLeft);
        board.getRightPaddle().setPaddleController(keyHandlerRight);
    }


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        board.startGame();
    }

    public void run() {

        while (gameThread !=null){
            repaint();
            sleep(5);
        }
    }

    public void render(Board board,Graphics g){

        Graphics2D g2 = ((Graphics2D) g);
        g2.setColor(Color.WHITE);

        for (Entity entity : board.getGameEntities()) {
            if (entity instanceof Ball){

                renderBall(((Ball) entity),g2);
            }
            if (entity instanceof NormalPaddle){

                renderPlatform(((NormalPaddle) entity),g2);
            }
        }
        g2.dispose();
    }
    private void renderBall(Ball ball,Graphics2D g2){
        int radius = ball.getRadius();
        g2.fillOval((int)ball.getLocation().getX() - ball.getRadius()/2,(int)ball.getLocation().getY()- ball.getRadius()/2,radius,radius);
        //g2.setColor(Color.RED);
        //g2.fillOval((int)(ball.getLocation().getX() - ball.getRadius()/2),(int)(ball.getLocation().getY()- ball.getRadius()/2),radius/2,radius/2);

        //x = x-(r/2);
        //  y = y-(r/2);
    }
    private void renderPlatform(Paddle paddle, Graphics2D g2){
        g2.fillRect((int)paddle.getLocation().getX(),(int) paddle.getLocation().getY(),(int)paddle.getWidth(),(int)paddle.getHeight());
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        render(board,g);
    }

    private static void sleep(long timeMilli){
        try {
            Thread.sleep(timeMilli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
