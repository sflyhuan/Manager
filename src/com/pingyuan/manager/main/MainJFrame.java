package com.pingyuan.manager.main;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;
import java.awt.*;

public class MainJFrame extends JFrame {
    // 选项卡垂直
    JTabbedPane jtp = new JTabbedPane(JTabbedPane.LEFT);

    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JPanel jp3 = new JPanel();
    JPanel jp4 = new JPanel();
    JPanel jp5 = new JPanel();
    JPanel jp6 = new JPanel();



    public MainJFrame() {//构造函数
        jtp.setBackground(Color.WHITE);

        jtp.add("用户管理", jp1);
        jtp.add("配置管理", jp2);
        jtp.add("照相管理", jp3);
        jtp.add("自编资料管理", jp4);
        jtp.add("日志管理", jp5);
        jtp.add("接入设备管理", jp6);
        this.add(jtp);
        this.setTitle("平板助手管理系统");
        this.setSize(1100, 660);
        this.setLocation(0, 0);
        //显示窗口true
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
