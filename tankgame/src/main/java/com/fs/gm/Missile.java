package com.fs.gm;

import java.awt.*;

public class Missile {
    int x,y;//子弹位置
    static int hurt = 20;//子弹伤害
    static int SPEED =4,NSPEED=4;
    static int count =0;//杀死敌人数
    static int WIDTH = 5;//子弹的大小
    static int HEIGHT = 5;
    boolean good;//区分坦克好坏
    boolean live = true;//子弹的存活
    Tank.Direction kdirection;
    public static int getCount(){
        return count;
    }

    public boolean isLive(){
        return live;
    }

    public static void setNspeed(int NSPEED1){
        NSPEED=NSPEED1;
    }

    public static void setHurt(int hurt1){
        hurt = hurt1;
    }

    public Missile(int x,int y,Boolean good,Tank.Direction kdirection){
        this.x=x;
        this.y=y;
        this.good = good;
        this.kdirection=kdirection;
    }

    public void draw(Graphics g) {
        if(!live) {
            return;
        }
        else if(good){//根据好坏来设置子弹颜色
            g.setColor(Color.white);
            g.fillOval(x, y, WIDTH, HEIGHT);
        }else{
            g.setColor(Color.yellow);
            g.fillOval(x, y, WIDTH, HEIGHT);
        }
        move();//画子弹的时候就要移动
    }

    void move() {
        if(!good){//根据好坏来设置子弹速度
            SPEED=NSPEED;
        }else{
            SPEED=4;
        }
        switch(kdirection) {//通过方位判断速度
            case L:
                x -= SPEED;
                break;
            case LU:
                x -= SPEED;
                y -= SPEED;
                break;
            case U:
                y -= SPEED;
                break;
            case RU:
                x += SPEED;
                y -= SPEED;
                break;
            case R:
                x += SPEED;
                break;
            case RD:
                x += SPEED;
                y += SPEED;
                break;
            case D:
                y += SPEED;
                break;
            case LD:
                x -= SPEED;
                y += SPEED;
                break;
            case STOP:
                break;

        }
        //子弹越界了就要死
        if(x < 0 || y < 0 || x > 800 || y > 600) {
            live = false;
        }
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public boolean hitTank(Tank t) {
        if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood() ){
            if(t.isGood()){//玩家被击中减血
//                  new Thread(new Music(Music.PLAY_EXPLODE)).start();
                t.setLife(t.getLife()- hurt);
                if(t.getLife()<=0){
                    t.setLife(0);
                    t.setLive(false);
                }
            }else{//敌方直接死
//                  new Thread(new Music(Music.PLAY_EXPLODE)).start();
                t.setLive(false);
                count++;
            }
            this.live = false;
            Explode e = new Explode(x, y);
            GameFrame.explodes.add(e);
            return true;
        }
        return false;
    }

    public void hitTanks(java.util.List<Tank> tanks) {
        for(int i=0; i<tanks.size(); i++) {
            if(hitTank(tanks.get(i))) {//调用了hitTank方法
                tanks.remove(tanks.get(i));
            }
        }
    }

    public boolean hitWalls(Wall w) { // 子弹打到普通墙上
        if (this.live && this.getRect().intersects(w.getRect())) {
            this.live = false;//子弹消失
            GameFrame.walls.remove(w); // 子弹打到墙上时则移除此击中墙
            return true;
        }
        return false;
    }

    public boolean hitWalls(HardWall hw) {
        if (this.live && this.getRect().intersects(hw.getRect())) {
//          new Thread(new Music(Music.PLAY_HIT)).start();
            this.live = false;//子弹消失
            return true;
        }
        return false;
    }

    public boolean hitHome() { // 当子弹打到家时
        if (this.live && this.getRect().intersects(GameFrame.home.getRect())) {
            this.live = false;
            GameFrame.home.setLive(false); // 玩家接受一枪就死亡
            return true;
        }
        return false;
    }

}
