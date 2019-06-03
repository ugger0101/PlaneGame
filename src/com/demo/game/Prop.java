package com.demo.game;

import javax.tools.Tool;
import java.awt.*;
import java.util.Random;

public class Prop extends GameObject {
    public static Image image_prop;
    public int award;//获取奖励


    static {

        image_prop = Tools.loadImage("images/aixin.png");//加载图片

    }

    public Prop() {
        super(image_prop,(int)(Math.random()*GamePanel.PANEL_WIDTH-50), -1000, 60, 50, 0, 5);
        Random random = new Random();
        award = random.nextInt(2);

    }

    @Override
    //道具直线向下飞
    public void move() {
        this.y+=5;
        this.x+=0;
    }
    public boolean outofBound() {
        return this.y>GamePanel.PANEL_HEIGHT;
    }
}
