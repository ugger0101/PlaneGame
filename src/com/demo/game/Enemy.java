package com.demo.game;

import java.awt.*;

public class Enemy extends GameObject{
    public static Image[] image;
    public int score;
    public void setScore(int score) {
        this.score = score;
    }
    public Enemy() {

        super(image[0],(int)(Math.random()*GamePanel.PANEL_WIDTH-50), -70, 50, 50, 0, 4);
        score=100;//分数为100分
    }


    public int getScore(){
        return score;
    }


    static {
        image = new Image[3];
        image[0] = Tools.loadImage("images/el_0.gif");
        image[1] = Tools.loadImage("images/el_0_l.gif");
        image[2] = Tools.loadImage("images/el_0_r.gif");

    }
    @Override
    public void move() {

    }
    public void move(Plane plane){

        this.y+=y_increase;
        if(this.y > GamePanel.PANEL_HEIGHT) {
            y = -70;

            this.x = (int) (Math.random() * GamePanel.PANEL_WIDTH - 50);


        }
    //让敌机有一定的概率朝着英雄机方向移动
		if((int)(Math.random()*1000)>995){

        if(this.x+this.width-plane.x<-40) {//当敌机离太左

            this.x_increase=3;
            this.img=image[1];

        }else if (this.x-(plane.x+plane.width)>40) {//当敌机离太右
            this.x_increase=-3;
            this.img=image[2];

        }else {
            this.x_increase=0;
            this.img=image[0];
        }}
		this.x+=this.x_increase;//保持敌机惯性


    }

    public EnemyBullet shoot(){

        if((int)(Math.random()*1000)>980) {



            return new EnemyBullet(this.x+(this.width-EnemyBullet.Width)/2, this.y+this.height+10,this.x_increase);//这个10为微调距离
        }
        else {
            return null;
        }



    }
}
