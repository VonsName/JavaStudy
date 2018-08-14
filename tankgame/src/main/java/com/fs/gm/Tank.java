package com.fs.gm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Random;

public class Tank implements KeyListener {
    private int x, y;// 坦克坐标
    private int oldX, oldY;// 坦克上一步坐标
    static int SPEED = 3;// 好坦克速度
    static int NSPEED = 3;// 坏坦克速度
    private int life = 100;// 生命值
    private boolean live = true;// 坦克存活
    private boolean good;// 区分坦克
    private static Random r = new Random();// 随机数产敌人数
    private int step; // 产生一个随机数,随机模拟坦克的移动路径
    boolean bU = false, bD = false, bL = false, bR = false;
    private Direction direction = Direction.STOP;// 坦克初始状态
    private Direction kdirection = Direction.U;// 坦克初始方向

    enum Direction {// 方向的枚举
        U, D, L, R, LU, LD, RU, RD, STOP
    };

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isLive() {
        return live;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public boolean isGood() {
        return good;
    }

    public static void setNspeed(int NSPEED1){
        NSPEED = NSPEED1;
    }

    // 判断范围（子弹进入坦克）
    public Rectangle getRect() {
        return new Rectangle(x, y, 40, 40);
    }

    static Image[] tanksImages = null;
    static {
        tanksImages = new Image[] {
                // 玩家坦克8个方向的图片
                new ImageIcon("images/tankU.gif").getImage(),
                new ImageIcon("images/tankD.gif").getImage(),
                new ImageIcon("images/tankL.gif").getImage(),
                new ImageIcon("images/tankR.gif").getImage(),
                new ImageIcon("images/tankLU.gif").getImage(),
                new ImageIcon("images/tankLD.gif").getImage(),
                new ImageIcon("images/tankRU.gif").getImage(),
                new ImageIcon("images/tankRD.gif").getImage(),
                // 敌方4个方向的图片
                new ImageIcon("images/EtankU.png").getImage(),
                new ImageIcon("images/EtankD.png").getImage(),
                new ImageIcon("images/EtankL.png").getImage(),
                new ImageIcon("images/EtankR.png").getImage(),
        };
    }

    // 坦克的构造方法
    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.good = good;
        this.oldX = x;
        this.oldY = y;
    }

    public Tank(int x, int y, boolean good, Direction direction) {
        this(x, y, good);
        this.direction = direction;
    }

    // 画坦克的方法
    public void draw(Graphics g) {
        if (live && good ) {
            switch (kdirection) {
                case U:
                    g.drawImage(tanksImages[0], x, y, null);
                    break;
                case D:
                    g.drawImage(tanksImages[1], x, y, null);
                    break;
                case L:
                    g.drawImage(tanksImages[2], x, y, null);
                    break;
                case R:
                    g.drawImage(tanksImages[3], x, y, null);
                    break;
                case LU:
                    g.drawImage(tanksImages[4], x, y, null);
                    break;
                case LD:
                    g.drawImage(tanksImages[5], x, y, null);
                    break;
                case RU:
                    g.drawImage(tanksImages[6], x, y, null);
                    break;
                case RD:
                    g.drawImage(tanksImages[7], x, y, null);
                    break;
            }
        }else if(live&&!good){
            switch (kdirection) {
                case U:
                    g.drawImage(tanksImages[8], x, y, null);
                    break;
                case D:
                    g.drawImage(tanksImages[9], x, y, null);
                    break;
                case L:
                    g.drawImage(tanksImages[10], x, y, null);
                    break;
                case R:
                    g.drawImage(tanksImages[11], x, y, null);
                    break;
            }
        }

        move();
    }

    void move() {
        this.oldX = x;
        this.oldY = y;
        if(!good){
            SPEED = NSPEED;
        }else{
            SPEED = 3;
        }
        switch (direction) {
            case U:
                y -= SPEED;
                break;
            case D:
                y += SPEED;
                break;
            case L:
                x -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case LU:
                x -= SPEED;
                y -= SPEED;
                break;
            case LD:
                x -= SPEED;
                y += SPEED;
                break;
            case RU:
                x += SPEED;
                y -= SPEED;
                break;
            case RD:
                x += SPEED;
                y += SPEED;
                break;
            case STOP:
                break;
        }
        if (this.direction != Direction.STOP)// 判断停止方向与行驶方向是否一致
            this.kdirection = this.direction;
        if (x < 0)x = 0;
        if (y < 0)y = 0;
        if (x > 750)x = 750;
        if (y > 500)y = 500;

        if (!good) {
            Direction[] directons = Direction.values();
            if (step == 0) {
                step = r.nextInt(20) + 20;// 产生随机路径
                int rn = r.nextInt(4);
                direction = directons[rn];// 产生随机方向
            }
            step--;
            if (r.nextInt(40) > 38)// 产生随机数，控制敌人开火
                this.fire();
        }

    }

    // 发出一发子弹的方法，向missiles里面添加子弹
    public Missile fire() {
        int x = this.x + 15;
        int y = this.y + 15;
        Missile m = new Missile(x, y,good,kdirection);
        GameFrame.missiles.add(m);
        return m;
    }

    public void locateDirection() {
        if(bL && !bU && !bR && !bD) direction = Direction.L;
        else if(bL && bU && !bR && !bD) direction = Direction.LU;
        else if(!bL && bU && !bR && !bD) direction = Direction.U;
        else if(!bL && bU && bR && !bD) direction = Direction.RU;
        else if(!bL && !bU && bR && !bD) direction = Direction.R;
        else if(!bL && !bU && bR && bD) direction = Direction.RD;
        else if(!bL && !bU && !bR && bD) direction = Direction.D;
        else if(bL && !bU && !bR && bD) direction = Direction.LD;
        else if(!bL && !bU && !bR && !bD) direction = Direction.STOP;
    }

    // 存放撞击后前的坐标
    private void changToOldDirection() {
        this.x = oldX;
        this.y = oldY;
    }

    // 判断坦克是否撞普通墙
    public boolean collideWall(Wall w) {
        if (this.live && this.getRect().intersects(w.getRect())) {
            this.changToOldDirection();// 转换到原来的坐标上
            return true;
        }
        return false;
    }

    // 判断坦克是否撞金属墙
    public boolean collideHardWall(HardWall hw) {
        if (this.live && this.getRect().intersects(hw.getRect())) {
            this.changToOldDirection();// 转换到原来的坐标上
            return true;
        }
        return false;

    }

    // 判断敌方坦克是否相撞
    public boolean collideWithTanks(List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);
            if (this != t) {
                if (this.live && t.isLive()&& this.getRect().intersects(t.getRect())) {
                    this.changToOldDirection();// 玩家坦克转换到原来的坐标上
                    t.changToOldDirection();// 敌方坦克转换到原来的坐标上
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                bU = true;
                break;
            case KeyEvent.VK_S:
                bD = true;
                break;
            case KeyEvent.VK_A:
                bL = true;
                break;
            case KeyEvent.VK_D:
                bR = true;
                break;
            case KeyEvent.VK_J:
                fire();
//          new Thread(new Music(Music.PLAY_FIRE)).start();
                break;
            case KeyEvent.VK_R:
                setLife(200);
                setLive(true);
                break;
        }
        locateDirection();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W:
                bU = false;
                break;
            case KeyEvent.VK_S:
                bD = false;
                break;
            case KeyEvent.VK_A:
                bL = false;
                break;
            case KeyEvent.VK_D:
                bR = false;
                break;
        }
        locateDirection();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }
}
