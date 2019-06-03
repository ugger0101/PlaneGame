package com.demo.game;

import java.awt.*;

public abstract class GameObject {
    protected Image img;//图片
    protected int x;//图片的X坐标
    protected int y;//图片的Y坐标
    protected int width;//图宽
    protected int height;//图高
    protected int x_increase;//图片移动的x坐标
    protected int y_increase;//图片移动的y坐标

    public GameObject(Image img, int x, int y, int width, int height, int x_increase, int y_increase) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.x_increase = x_increase;
        this.y_increase = y_increase;
    }

    public void draw(Graphics g){
        g.drawImage(img,x,y,width,height,null);

    }

    abstract public void move();

    public boolean isHitted(GameObject obj){
         return  this.x+this.width > obj.x  &&  this.x < obj.x+obj.width
                &&  this.y+this.height > obj.y  &&  this.y < obj.y+obj.height;

    }






}
