package com.demo.game;

import java.awt.*;

public class EnemyBullet extends GameObject {
    public static final int Width = 20;
    public static final int Height = 20;
    private int img_cont;
    public static Image[] images_enemybullet;

    static {
        images_enemybullet = new Image[2];
        images_enemybullet[0] = Tools.loadImage("images/el_bb_0.gif");
        images_enemybullet[1] = Tools.loadImage("images/el_bb_0.gif");
    }

    public EnemyBullet(int x, int y, int x_increase) {
        super(images_enemybullet[0], x, y, Width, Height, x_increase, 6);

        this.img_cont = 0;
    }





    @Override
    public void move() {
        this.x+=x_increase;
        this.y+=y_increase;
        img_cont++;
        //5表示，每间隔5个30ms（30ms是GamePanel中的Thread.sleep）
        this.img=images_enemybullet[img_cont/5%2];

    }
    public boolean outOfBounds() {
        return this.y>GamePanel.PANEL_HEIGHT;

    }
}
