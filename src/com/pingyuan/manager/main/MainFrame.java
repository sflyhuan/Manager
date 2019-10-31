package com.pingyuan.manager.main;

import com.pingyuan.manager.equipment.EquipmentPanel;
import com.pingyuan.manager.logs.LogPanel;
import com.pingyuan.manager.other.OtherPanel;
import com.pingyuan.manager.users.UsersPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    // 水平垂直
    JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);

    public MainFrame() {//构造函数
        jtp.setBackground(Color.WHITE);
        jtp.add("接入设备管理", new EquipmentPanel());
        jtp.add("用户管理", new UsersPanel(this));
        jtp.add("日志管理", new LogPanel(this));
        jtp.add("配置管理", new OtherPanel());

        this.add(jtp);
        this.setTitle("XXXX平板助手管理端");
        ImageIcon icon = new ImageIcon(MainFrame.class.getResource("/resources/icon.jpg"));//图片位于工程根目录
        this.setIconImage(icon.getImage());
        this.setSize(1100, 660);
        this.setLocation(0, 0);
        this.setResizable(false); //禁止改变窗体大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true); //显示窗口true
    }
}
