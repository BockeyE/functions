package com.game;

import javax.swing.*;
import java.awt.*;

/**
 * @author bockey
 * 自由落体、飞翔上升
 */
public class Bird extends Thread {
    // 鸟的位置
    private int x;
    private int y;

    // 保持初始位置
    private int oldx;
    private int oldy;
    private int old2x;
    private int old2y;

    // 鸟的图片
    private Image[] bird = {
            new ImageIcon(getClass().getResource("/Birds_01.png")).getImage(),
            new ImageIcon(getClass().getResource("/Birds_02.png")).getImage(),
            new ImageIcon(getClass().getResource("/Birds_03.png")).getImage(),
    };

    private int imageIndex = 0;

    // 鸟下落的速度 、上升的速度
    private int downV;
    private int upV;
    private int upVn = 0;

    // 鸟上升的大小
    private int up;

    // 下降或上升
    private static int DOWN = 0;
    private static int FLY = 1;
    // 下降或上升的标志
    private int flag = Bird.FLY;

    // 自由落体的重力加速度
    private double g = 0.0003;


    public Bird(int x, int y, int downV, int upV, int up) {
        this.x = x;
        this.y = y;
        this.downV = downV;
        this.upV = upV;
        this.up = up;
        this.oldx = x;
        this.oldy = y;
        old2x = x;
        old2y = y;

        this.upVn = upV;

    }

    public void setImageIndex() {
        if (imageIndex == 2) {
            imageIndex = 0;
        } else {
            imageIndex++;
        }
    }


    public void drawSelf(Graphics graphics) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        double a = Math.atan((y - old2y + 0.000001) / 50);
        if (a > Math.atan(2)) {
            a = Math.atan(2);
        } else if (a < Math.atan(-2)) {
            a = Math.atan(-2);
        }
        if (GameUI.flag == 1) {
            graphics2D.rotate(a, x + 17, y + 17);
        }
        graphics.drawImage(bird[imageIndex], x, y, bird[imageIndex].getWidth(null),
                bird[imageIndex].getHeight(null), null);
        if (GameUI.flag == 1) {
            graphics2D.rotate(-a, x + 17, y + 17);
        }
    }

    public void setStatus() {
        if (flag == Bird.DOWN) {
            flag = Bird.FLY;
            old2x = x;
            old2y = y;
            GameUI.start = System.currentTimeMillis();
        } else {
            flag = Bird.DOWN;
            old2x = x;
            old2y = y;
            GameUI.start = System.currentTimeMillis();
            upV = upVn;
        }
    }

    public void setFlyStatus() {
        flag = Bird.DOWN;
        old2x = x;
        old2y = y;
        GameUI.start = System.currentTimeMillis();
        upV = upVn;
    }


    // 获取小鸟的位置
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // 重新开始
    public void reStart() {
        x = oldx;
        y = oldy;
        old2x = x;
        old2y = y;
    }

    // 运动方法
    public void move() {
        if (GameUI.flag == 0) {
            setImageIndex();
            return;
        } else if (GameUI.flag == 2) {
            return;
        }
        if (flag == Bird.DOWN) {
            long end = System.currentTimeMillis();
            long t = (end - GameUI.start);
            int oy = (int) (old2y + 0.8 * g * t * t);
            y = oy;
        } else {
            y--;
            long end = System.currentTimeMillis();
            long t = (end - GameUI.start);
            upV += 0. * g * t;
            if ((upVn - 60 * g * t) <= 0) {
                setStatus();
            }
            if (y <= 0) {
                y = 0;
            }
        }
        setImageIndex();
    }

    // 线程，让鸟一直运动
    @Override
    public void run() {
        while (true) {
            move();
            try {
                if (flag == Bird.FLY) {
                    sleep(upV);
                } else {
                    sleep(downV);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

