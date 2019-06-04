package com.demo.game;

import java.awt.*;

public class PlaneBullet extends GameObject {
    public static Image[] image_fire;
    public static final int width = 30;//自己子弹图宽
    public static final int height = 30;//自己子弹图高
    private int img_cont;


    //静态初始化块初始图片
    static {
        image_fire = new Image[2];
        image_fire[1] = Tools.loadImage("images/fire.gif");
        image_fire[2] = Tools.loadImage("images/fire_1.gif");
    }

    public PlaneBullet(int x, int y) {
        super(image_fire[0], x, y, width, height, 0, 9);
         img_cont =0;
    }

    public void move(){
        this.y -= y_increase;
        this.x -= x_increase;
        img_cont++;
        this.img = image_fire[img_cont/5%2];

    }
    public boolean outofBound(){
        return this.y<0;
    }

}
