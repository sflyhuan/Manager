package com.pingyuan.manager.main;

import com.pingyuan.manager.equipment.EquipmentPanel;
import com.pingyuan.manager.logs.LogPanel;
import com.pingyuan.manager.other.OtherPanel;
import com.pingyuan.manager.users.UsersPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    // 选项卡垂直
    JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);

    EquipmentPanel equipmentPanel = new EquipmentPanel();  //设备
    UsersPanel usersPanel = new UsersPanel(this);
    LogPanel logPanel = new LogPanel(this);
    OtherPanel otherPanel = new OtherPanel();

    public MainFrame() {//构造函数
        jtp.setBackground(Color.WHITE);
        jtp.add("接入设备管理", equipmentPanel);
        jtp.add("用户管理", usersPanel);
        jtp.add("日志管理", logPanel);
        jtp.add("配置管理", otherPanel);

        this.add(jtp);
        this.setTitle("XXXX平板助手管理端");
        this.setSize(1100, 660);
        this.setLocation(0, 0);
        this.setResizable(false); //禁止改变窗体大小
        //显示窗口true
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon(MainFrame.class.getResource("/resources/icon.jpg"));//图片位于工程根目录
        this.setIconImage(icon.getImage());
    }

}
