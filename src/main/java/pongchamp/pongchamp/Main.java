package pongchamp.pongchamp;

import pongchamp.pongchamp.view.SimpleRenderEngine;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("PongChamp");

        SimpleRenderEngine renderEngine = new SimpleRenderEngine();
        window.add(renderEngine);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
