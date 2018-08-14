package com.fs.gm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame implements ActionListener,Runnable {
    static Tank hero = new Tank(200, 370, true, Tank.Direction.STOP);
    static int enemyNumber = 1;
    static int round = 1;

    static List<Tank> tanks = new ArrayList<Tank>();// 装坦克的容器
    {
        tanks.add(new Tank(100 , 50, false, Tank.Direction.D));
    }

    static List<Missile> missiles = new ArrayList<Missile>();// 装子弹的容器

    static List<Explode> explodes = new ArrayList<Explode>();// 装爆炸的容器

    static List<Wall> walls = new ArrayList<Wall>();// 装普通墙的容器

    static List<HardWall> hWalls = new ArrayList<HardWall>();// 装金属墙的容器

    {
        for (int i = 0; i < 10; i++) {
            // 初始墙布局的位置
            walls.add(new Wall(50 + 30 * i, 300));
            walls.add(new Wall(250, 50 + 30 * i));
            walls.add(new Wall(350 + 30 * i, 100));
            walls.add(new Wall(500, 180 + 30 * i));
        }
        for(int i = 0; i < 6; i++){
            hWalls.add(new HardWall(340, 240 + 30 * i));
            hWalls.add(new HardWall(220 + 30 * i, 200));
        }
    }

    public static Boolean play = true;// 决定音乐的开启与关闭

    public static boolean printable = true;// 决定线程的开启与关闭

    static Home home = new Home(290, 260);

    public GameFrame() {

        addKeyListener(hero);

        createMenu();// 创建菜单

        setTitle("坦克大战");
        setVisible(true);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Thread th = new Thread(this);
        th.start();

        // 在面板上绘制坦克、子弹、墙、老家
        JPanel jp = new JPanel() {
            {
                setBackground(Color.gray);// 设置面板背景颜色
            }

            public void paint(Graphics g) {
                super.paint(g);
                g.drawString("你的分数：" + Missile.getCount(), 10, 20);
                g.drawString("你的生命值：" + hero.getLife(), 10, 40);
                g.drawString("第：" + round + " 轮战斗" + "，敌人总数："+ tanks.size(), 10, 60);
                g.drawRect(45, 515, 700, 15);
                g.fillRect(45, 515, hero.getLife()*7, 15);

                // 把容器里面的坦克都画出来
                for (int i = 0; i < tanks.size(); i++) {
                    Tank t = tanks.get(i);
                    t.draw(g);

                    //画出普通墙
                    for (int j = 0; j < walls.size(); j++) {
                        Wall w = walls.get(j);
                        w.draw(g);
                        t.collideWall(w);// 每一个坦克撞到普通墙
                        hero.collideWall(w);
                    }

                    // 画出金属墙
                    for (int n = 0; n < hWalls.size(); n++) {
                        HardWall hw = hWalls.get(n);
                        hw.draw(g);
                        t.collideHardWall(hw);// 每一个坦克撞到金属墙
                        hero.collideHardWall(hw);
                    }

                    t.collideWithTanks(tanks); // 撞到自己的人

                }

                //每关卡的敌人设置
                if(tanks.size()==0 && round<6){
                    for (int i = 0; i < enemyNumber*2; i++) {
                        Tank t=null;
                        if(i<2){
                            t=new Tank(100 + 70 * i, 50, false, Tank.Direction.L);
                        }else if(i>3){
                            t=new Tank(450, i * 50 + 20, false, Tank.Direction.R);
                        }else{
                            t=new Tank(50+i * 50 , 500, false, Tank.Direction.U);
                        }
                        tanks.add(t);
                    }
                    enemyNumber++;
                    round++;
                }


                // 把容器里面的子弹都画出来
                for (int i = 0; i < missiles.size(); i++) {
                    Missile m = missiles.get(i);
                    m.hitTanks(tanks);// 玩家子弹攻击敌方
                    m.hitTank(hero);// 敌方子弹攻击玩家
                    m.hitHome();//敌方子弹攻击老家
                    m.draw(g);//画子弹

                    for (int j = 0; j < walls.size(); j++) {// 每一个子弹打到其他墙上
                        Wall w = walls.get(j);
                        m.hitWalls(w);
                    }
                    for (int j = 0; j < hWalls.size(); j++) {// 每一个子弹打到金属墙上
                        HardWall hw = hWalls.get(j);
                        m.hitWalls(hw);
                    }
                }

                // 把容器里面的爆炸全部画出来
                for (int i = 0; i < explodes.size(); i++) {
                    Explode e = explodes.get(i);
                    e.draw(g);
                }

                hero.draw(g);// 画出玩家的坦克
                home.draw(g);//画出自己的家
                hero.collideWithTanks(tanks);//玩家坦克撞上敌方坦克

            }
        };
        add(jp);
    }

    // 创建菜单栏
    public void createMenu() {
        // 菜单栏
        JMenuBar jmb = new JMenuBar();
        // 菜单项
        JMenu jm1 = new JMenu("游戏");
        JMenu jm2 = new JMenu("历史记录");
        JMenu jm3 = new JMenu("帮助");
        JMenu jm4 = new JMenu("游戏难度");
        // 菜单项按钮
        JMenuItem jmi1 = new JMenuItem("暂停");
        JMenuItem jmi2 = new JMenuItem("继续");
        JMenuItem jmi10 = new JMenuItem("重新开始");
        JMenuItem jmi3 = new JMenuItem("背景音乐开/关");
        JMenuItem jmi11 = new JMenuItem("返回到主界面");
        JMenuItem jmi4 = new JMenuItem("历史排名");
        JMenuItem jmi5 = new JMenuItem("玩家得分记录");
        JMenuItem jmi6 = new JMenuItem("关于游戏");
        JMenuItem jmi7 = new JMenuItem("普通模式");
        JMenuItem jmi8 = new JMenuItem("人间模式");
        JMenuItem jmi9 = new JMenuItem("地狱模式");


        // 添加菜单
        jmb.add(jm1);
        jmb.add(jm2);
        jmb.add(jm4);
        jmb.add(jm3);

        // 设置游戏菜单项
        jm1.add(jmi1);
        jm1.add(jmi2);
        jm1.add(jmi10);
        jm1.add(jmi3);
        jm1.add(jmi11);
//      jm2.add(jmi4);
        jm2.add(jmi5);
        jm3.add(jmi6);
        jm4.add(jmi7);
        jm4.add(jmi8);
        jm4.add(jmi9);

        // 为按钮添加监听事件
        jmi1.addActionListener(this);
        jmi1.setActionCommand("stop");

        jmi2.addActionListener(this);
        jmi2.setActionCommand("continue");

        jmi3.addActionListener(this);
        jmi3.setActionCommand("music");

        jmi4.addActionListener(this);
        jmi4.setActionCommand("rank");

        jmi5.addActionListener(this);
        jmi5.setActionCommand("history");

        jmi6.addActionListener(this);
        jmi6.setActionCommand("help");

        jmi7.addActionListener(this);
        jmi7.setActionCommand("difficulty1");

        jmi8.addActionListener(this);
        jmi8.setActionCommand("difficulty2");

        jmi9.addActionListener(this);
        jmi9.setActionCommand("difficulty3");

        jmi10.addActionListener(this);
        jmi10.setActionCommand("restart");

        jmi11.addActionListener(this);
        jmi11.setActionCommand("back");

        // 将菜单放在窗体上
        this.setJMenuBar(jmb);
    }

    public static void main(String[] args) {
        new GameFrame();
    }

    public void run() {
        // 每隔100毫秒 重新画图
        while (printable) {
            try {// 记录玩家分数
                if (Missile.getCount()==31 || !hero.isLive()) {
                    printable = false;// 停止线程
                    if(Missile.getCount()==31)JOptionPane.showMessageDialog(null, "您赢得了比赛！");
                    if(!hero.isLive())JOptionPane.showMessageDialog(null, "您输掉了比赛！");
                    if(!play)Music.aau.stop();//判断是否关闭音乐
                    try {
                        FileWriter fw = new FileWriter("txt/score.txt", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write("\t\t玩家" + StartFrame.getUserName() + "分数为：" + Missile.getCount() + "\n");// 往已有的文件上添加字符串
                        bw.close();
                        fw.close();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                Thread.sleep(20);
            } catch (Exception e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("stop")) {
            printable = false;// 停止线程
        }

        if (e.getActionCommand().equals("continue")) {
            if (!printable) {
                printable = true;
                new Thread(this).start(); // 线程启动
            }
        }

        if (e.getActionCommand().equals("music")) {
            Music.musicBack(play);
            play = !play;//点击播放后置为false
        }

        if (e.getActionCommand().equals("restart")) {
            printable = false;
            Object[] options = { "确定", "取消" };
            int response = JOptionPane.showOptionDialog(this, "您确认要开始新游戏！", "",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[0]);
            if (response == 0) {
                //初始化所有变量
                printable = true;
                Home.setLive(true);//重新激活老家
                GameFrame.hero.setLife(100);
                GameFrame.hero.setLive(true);//重新激活玩家坦克
                Missile.count=0;//重置得分
                round=1;//重置轮数
                enemyNumber = 1;//重置敌人数量
                Tank.setNspeed(3);//敌人坦克速度
                Missile.setNspeed(4);//敌人坦克子弹速度
                Missile.setHurt(20);//敌人坦克伤害
                tanks.clear();//坦克清空
                missiles.clear();//子弹清空
                walls.clear();//墙清空
                this.dispose();//关闭当前窗口
                new GameFrame();
            } else {
                printable = true;
                new Thread(this).start(); // 线程启动
            }
        }

        if(e.getActionCommand().equals("back")){
            printable = false;
            Object[] options = { "确定", "取消" };
            int response = JOptionPane.showOptionDialog(this, "您确认要返回到主界面！", "",JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[0]);
            if(response == 0){
                tanks.clear();
                GameFrame.hero.setLife(100);
                this.dispose();
                new StartFrame();
                printable = true;
            }else {
                printable = true;
                new Thread(this).start(); // 线程启动
            }
        }

        if (e.getActionCommand().equals("difficulty1")) {
            tanks.clear();
            missiles.clear();
            walls.clear();
            Tank.setNspeed(7);//改变敌人坦克速度
            Missile.setNspeed(6);//改变敌人子弹速度
            this.dispose();
            new GameFrame();
        }

        if (e.getActionCommand().equals("difficulty2")) {
            tanks.clear();
            missiles.clear();
            walls.clear();
            Tank.setNspeed(9);
            Missile.setNspeed(6);
            Missile.setHurt(30);//敌人坦克伤害
            this.dispose();
            new GameFrame();
        }

        if (e.getActionCommand().equals("difficulty3")) {//没清除墙，相当于两堵墙叠加在一起要打两次才消失
            tanks.clear();
            missiles.clear();
            Tank.setNspeed(11);
            Missile.setNspeed(8);
            Missile.setHurt(40);
            this.dispose();
            new GameFrame();
        }

        if (e.getActionCommand().equals("history")) {
            try {
                new ScoreFrame();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        if (e.getActionCommand().equals("rank")) {
            //
        }

        if (e.getActionCommand().equals("help")) {
            printable = false;// 停止线程
            JOptionPane.showMessageDialog(null, "W、向上，A、向下，S、向下，D、向上，J、发射炮弹，R、加血"+"\n"+"开发者：熊竞"+"\n"+"联系作者（qq）：80594070","提示",JOptionPane.INFORMATION_MESSAGE);
            printable = true;
            new Thread(this).start(); // 线程启动
        }
    }

    public void actionPerformed(ActionEvent actionEvent) {

    }
}
