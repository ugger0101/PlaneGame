package com.demo.game;


import java.awt.*;

public class Background extends GameObject {
    public static Image image;
    static {
        image = Tools.loadImage("images/back.jpg");
    }

    public Background() {
        super(image, 0, -5*GamePanel.PANEL_HEIGHT, GamePanel.PANEL_WIDTH,6* GamePanel.PANEL_HEIGHT, 0, 2);
        }

    @Override
    public void move() {
        this.y += this.y_increase;
        if (this.y>0) {
            this.y=0;
        }

    }
}
