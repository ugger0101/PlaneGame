package com.demo.game;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, MouseListener,MouseMotionListener {


    public static final int PANEL_WIDTH = GameFrame.Frame_width - 7;
    public static final int PANEL_HEIGHT = GameFrame.Frame_height - 30;
    public static final int MAX_Enemy = 9;



    private Background background;//天空背景对象

    private Plane plane;//英雄机对象
    private ArrayList<Enemy> enemys;//敌机对象

    private ArrayList<EnemyBullet> enemybullets;//敌机子弹类对象
    private ArrayList<PlaneBullet> planeBullets;//英雄机子弹对象
    private ArrayList<Boom> booms;//爆破对象
    private Prop prop;//道具对象


    private int total_score = 0;//游戏总得分
    public int maxscore;
    //AudioClip对象提供  play loop（循环） stop
    java.applet.AudioClip all_bomb, enemy_bomb, bg, hero_bomb, hero_bullet;

    public GamePanel() {
        try {
            all_bomb = Applet.newAudioClip(new File("musics/all_bomb.wav").toURI().toURL());
            enemy_bomb = Applet.newAudioClip(new File("musics/enemy_bomb.wav").toURI().toURL());
            bg = Applet.newAudioClip(new File("musics/bg.wav").toURI().toURL());
            hero_bomb = Applet.newAudioClip(new File("musics/hero_bomb.wav").toURI().toURL());
            hero_bullet = Applet.newAudioClip(new File("musics/hero_bullet.wav").toURI().toURL());


        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        background = new Background();//初始化背景
        enemys = new ArrayList<Enemy>();//初始化敌机
        //初始化敌机最大数量
        for (int i = 0; i < MAX_Enemy; i++) {
            enemys.add(new Enemy());
        }
        plane = new Plane();//初始化自己个
        enemybullets = new ArrayList<EnemyBullet>();//敌机子弹
        planeBullets = new ArrayList<PlaneBullet>();//自己个子弹
        booms = new ArrayList<Boom>();//初始化爆炸
        prop = new Prop();//prop
        this.addMouseMotionListener(this);
        this.addMouseListener(this);


    }


    @Override

    //重写jpanel的画图，将部分游戏内的图先初始化，并画上去
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        background.draw(g);//背景
        for (int i = 0; i < enemys.size(); i++) {//敌机
            Enemy enemy1 = enemys.get(i);
            enemy1.draw(g);
        }
        plane.draw(g);//飞机

        for (int i = 0; i < planeBullets.size(); i++) {//自己个子弹
            PlaneBullet planeBullet1 = planeBullets.get(i);
            planeBullet1.draw(g);
        }

        for (int i = 0; i < enemybullets.size(); i++) {//敌机子弹
            EnemyBullet enemyBullet1 = enemybullets.get(i);
            enemyBullet1.draw(g);
        }
        for (int i = 0; i < booms.size(); i++) {//爆破效果
            Boom boom1 = booms.get(i);
            boom1.draw(g);
        }







        prop.draw(g);
        g.setColor(Color.GREEN);
        g.setFont(new Font("宋体", Font.BOLD, 30));
        g.drawString("生命数" + plane.getLife(), 10, 30);
        g.drawString("分数" + total_score, 10, 80);
        if(total_score>CRUD.select()) {
            CRUD.update1(total_score);
        }
        maxscore =  CRUD.select();//最大分数
        g.drawString("最高分"+maxscore,10,130);
        if (plane.getLife() == 0) {

            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑", Font.BOLD, 50));
            g.drawString("Game over!", PANEL_WIDTH / 3 - 40, PANEL_HEIGHT / 2);

        }


    }
    //数据库接入







    @Override
    public void run() {
        bg.play();
        while (true) {
            //1背景图移动
            background.move();

            //2对敌机的处理
            for (int i = 0; i < enemys.size(); i++) {

                Enemy enemy1 = enemys.get(i);
                //2.1敌机移动
                enemy1.move(plane);

                //2.2敌机发射子弹
                EnemyBullet enemybullet = enemy1.shoot();
                if (enemybullet != null) {

                    enemybullets.add(enemybullet);
                }

            }

            for (int i = 0; i < enemybullets.size(); i++) {
                EnemyBullet enemybullet = enemybullets.get(i);
                //3.1移动
                enemybullet.move();
                //3.2是否出界
                if (enemybullet.outOfBounds()) {
                    enemybullets.remove(i);
                    i--;
                }
            }

                //对蜜蜂处理
                prop.move();
                if (prop.outofBound()) {
                    prop.y = -4000;
                    Random rand = new Random();
                    prop.x = rand.nextInt(PANEL_WIDTH - this.WIDTH);
                }

                //4对英雄机子弹的处理
                //4.1移动
                for (int i = 0; i < planeBullets.size(); i++) {
                    PlaneBullet planeBullet = planeBullets.get(i);
                    planeBullet.move();
                    //4.2是否越界

                    if (planeBullet.outofBounds()) {
                        planeBullets.remove(i);
                        i--;
                    }
                }


                //5.碰撞检测


                //5.1 检测敌机与英雄机，英雄机子弹的碰撞情况
                boolean heroPlane_ishitted = false;//记录英雄机被撞
                for (int i = 0; i < enemys.size(); i++) {

                    Enemy enemy1 = enemys.get(i);
                    boolean ePlane_isHitted = false;//记录敌机被撞
                    //5.1.1跟英雄机撞
                    if (enemy1.isHitted(plane)) {
                        ePlane_isHitted = true;
                        Boom boom1 = new Boom(plane.x, plane.y, plane.width, plane.height, plane.x_increase, plane.y_increase);
                        booms.add(boom1);
                        plane.setlife(plane.getLife() - 1);//生命数-1

                        //加上heroplane的高，为了防止出界后的敌机与英雄机相撞
                        plane.move(0, (GamePanel.PANEL_HEIGHT + plane.height));

                        //移除鼠标监听器，暂时无法移动
                        this.removeMouseListener(this);
                        this.removeMouseMotionListener(this);
                        plane.setrestart(40);//设置英雄机重启，需要延迟40个50ms
                        plane.setalive(false);


                    }


                    //5.1.2跟英雄机子弹是否相撞
                    for (int j = 0; j < planeBullets.size(); j++) {
                        PlaneBullet heroBullet = planeBullets.get(j);
                        if (enemy1.isHitted(heroBullet)) {
                            ePlane_isHitted = true;
                            planeBullets.remove(j);//移除英雄机子弹
                            j--;

                        }
                        //蜜蜂撞上了
                        if (prop.isHitted(heroBullet)) {
                            Random random = new Random();
                            prop.y = -2000;
                            prop.x = random.nextInt(PANEL_WIDTH) - 80;
                            planeBullets.remove(j);
                            i--;

                            plane.setlife(plane.getLife() + 1);
                        }

                    }
                        if (ePlane_isHitted) {//若敌机被撞

                            total_score += enemy1.getScore();
                            hero_bullet.play();
                            enemys.remove(i);//移除该敌机
                            i--;
                            Boom boom = new Boom(enemy1.x, enemy1.y, enemy1.width, enemy1.height, enemy1.x_increase, enemy1.y_increase);
                            booms.add(boom);

                        }


                }
                //5.2检测英雄机和敌机子弹的碰撞情况
                //关于英雄机和敌机碰撞，见5.1

                for (int i = 0; i < enemybullets.size(); i++) {
                    EnemyBullet enemybullet = enemybullets.get(i);
                    if (plane.isHitted(enemybullet)) {
                        heroPlane_ishitted = true;
                        enemybullets.remove(i);//移除该子弹
                        i--;


                    }
                }


                if (heroPlane_ishitted) {//如果英雄机被撞了
                    hero_bomb.play();
                    Boom boom = new Boom(plane.x, plane.y, plane.width, plane.height, plane.x_increase, plane.y_increase);
                    booms.add(boom);
                    plane.setlife(plane.getLife() - 1);//生命数-1


                    //加上heroplane的高，为了防止出界后的敌机与英雄机相撞
                    plane.move(0, GamePanel.PANEL_HEIGHT + plane.height);


                    //移除鼠标监听器，暂时无法移动
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    plane.setrestart(40);//设置英雄机重启，需要延迟40个50ms(参见thread.sleep)
                    plane.setalive(false);


                }


                //6对爆破对象进行处理
                for (int i = 0; i < booms.size(); i++) {
                    Boom boom = booms.get(i);
                    boom.move();
                    if (boom.Boomend()) {//该爆破结束

                        booms.remove(i);
                    }
                }
                //7对英雄机状态处理
                if (plane.getLife() > 0) {


                    if (plane.getrestart() > 0) {
                        plane.setrestart(plane.getrestart() - 1);
                    }

                    //如果英雄机当前死亡，且重启延时已经为0
                    if (plane.isAlive() == false && plane.getrestart() == 0) {
                        plane.move(GamePanel.PANEL_WIDTH / 2 - 35, GamePanel.PANEL_HEIGHT - 70);
                        plane.setalive(true);
                        addMouseListener(this);
                        addMouseMotionListener(this);

                        //添加一个全屏爆炸

                        all_bomb.play();
                        Boom boom = new Boom(0, 0, PANEL_WIDTH, PANEL_HEIGHT, 0, 0);
                        booms.add(boom);

                        for (int i = 0; i < enemys.size(); i++) {//清除屏幕得分
                            Enemy ePlane = enemys.get(i);
                            total_score += ePlane.getScore();


                        }

                        enemys.clear();//清空所有敌机
                        enemybullets.clear();//清空所有子弹

                    }
                }


                //逐渐补充敌机数到MAX_Enemy
                if (enemys.size() < MAX_Enemy) {
                    enemys.add(new Enemy());
                }

                if (plane.getLife() == 0) {
                    bg.stop();
                }
                repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }




        }




    }



    @Override
    public void mouseDragged(MouseEvent e) {

        plane.move(e.getX(),e.getY());
    }





    @Override
    public void mouseMoved(MouseEvent e) {

        //System.out.println(e.getX()+","+e.getY());
        plane.move(e.getX(),e.getY());


    }





    @Override
    public void mouseClicked(MouseEvent e) {


    }





    @Override
    public void mousePressed(MouseEvent e) {
        hero_bullet.play();
        PlaneBullet heroBullet=plane.shoot();
        planeBullets.add(heroBullet);

    }





    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }





    @Override
    public void mouseEntered(MouseEvent e) {

    }





    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }



}














