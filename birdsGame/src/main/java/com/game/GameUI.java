package com.game;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @author bockey
 */
public class GameUI extends JFrame implements MouseListener, MouseMotionListener {
    /*
     * 游戏界面组成
     * gamePanel : 游戏画面的容器，也是画板，主要绘制游戏界面
     * bird 	 : 游戏中的主角，即小鸟
     * pipe		 : 游戏中的障碍物，即管道
     * score 	 : 游戏得分
     */



    /*
     * 游戏状态标志
     * 0 ： 游戏未开始
     * 1 ： 游戏进行中
     * 2：  游戏结束
     */
    public static int flag = 0;

    //游戏开始时间
    public static long start = 0;


    //管道夹缝宽度
    public static final int w = 100;
    //两个管道之间的宽度
    public static final int d = 300;



    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }
}
