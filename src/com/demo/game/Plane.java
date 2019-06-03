package com.demo.game;

import java.awt.*;

public class Plane extends GameObject {
    public static Image image_me;
    public int life;//生命值
    public int restart;//重启时间延迟
    public double fire;//火力
    public boolean alive;//存活状态

    //初始化飞机图片
    static {
        image_me = Tools.loadImage("images/123.png");

    }


    public Plane(){
        super(image_me, GamePanel.PANEL_WIDTH/2-35, GamePanel.PANEL_HEIGHT-70, 70, 70, 0, 0);
        life=3;
        restart=0;//初始不需要延迟
        alive=true;
    }

    public boolean isAlive(){
        return  alive;

    }

    public boolean setalive(boolean alive){
        return this.alive = alive;
    }

    public int getrestart(){
        return restart;
    }

    public void setrestart(int restart){
        this.restart = restart;
    }
    //子弹需要从飞机前头中心射出
    public PlaneBullet shoot(){
        return new PlaneBullet(this.x+(this.width-PlaneBullet.width)/2,this.y-PlaneBullet.height);

    }


    @Override
    public void move() {

    }
    public void move(int x, int y) {
        this.x=x-35;
        this.y=y-35;
    }

    public int getLife() {
        return this.life;

    }

    public void setlife(int life){
        this.life = life;
    }
}
