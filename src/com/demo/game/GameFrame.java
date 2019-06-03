package com.demo.game;


import javax.swing.*;
import java.awt.*;

public class GameFrame {

    public static final int Frame_width = Toolkit.getDefaultToolkit().getScreenSize().width/3;
    public static final int Frame_height = Toolkit.getDefaultToolkit().getScreenSize().height;



    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(Frame_width,Frame_height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setTitle("Air attack");
        frame.setLocationRelativeTo(null);
        GamePanel gamePanela = new GamePanel();
        frame.add(gamePanela);
        frame.setVisible(true);
        Thread t = new Thread(gamePanela);
        t.start();

    }
}
