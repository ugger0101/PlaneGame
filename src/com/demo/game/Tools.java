package com.demo.game;

import java.awt.Image;
import java.awt.Toolkit;

public class Tools {//工具类，将比较麻烦的方法 专门建一个这样的工具类，用static静态方法在这里构造。

    public static Image loadImage(String Filename) {
        //Toolkit工具类Toolkit.getDefaultToolkit().getImage就是加载图片用的
        return Toolkit.getDefaultToolkit().getImage(Filename);


    }
}
