package com.demo.game;

import java.awt.*;

public class Boom extends GameObject{
    public static Image[] image;
    private int Img_cnt;//爆炸图片的加载次数的间隔

    static {
        image = new Image[4];
        image[0] = Tools.loadImage("images/bomb_0.gif");
        image[1] = Tools.loadImage("images/bomb_1.gif");
        image[2] = Tools.loadImage("images/bomb_2.gif");
        image[3] = Tools.loadImage("images/bomb_3.gif");

    }

    public Boom(int x, int y, int width, int height, int x_increase, int y_increase) {
        super(image[0], x, y, width, height, x_increase, y_increase);
        Img_cnt = 0;

    }



    @Override
    public void move() {
        this.y+=y_increase;
        this.x+=x_increase;
        Img_cnt ++;
        this.img=image[Img_cnt/4%4];


    }
    public boolean Boomend(){
        return Img_cnt==15;
    }
}
