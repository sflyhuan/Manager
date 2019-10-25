package com.pingyuan.manager.main;

import com.pingyuan.manager.equipment.EquipmentJPanel;
import com.pingyuan.manager.logs.LogsJPanel;
import com.pingyuan.manager.users.UsersJPanel;

import javax.swing.*;
import java.awt.*;

public class MainJFrame extends JFrame {
    // 选项卡垂直
    JTabbedPane jtp = new JTabbedPane(JTabbedPane.LEFT);

    EquipmentJPanel equipmentJPanel = new EquipmentJPanel();  //设备
    UsersJPanel usersJPanel = new UsersJPanel();
    LogsJPanel logsJPanel = new LogsJPanel();



    public MainJFrame() {//构造函数
        jtp.setBackground(Color.WHITE);
        jtp.add("接入设备管理", equipmentJPanel);
        jtp.add("用户管理", usersJPanel);
        jtp.add("日志管理", logsJPanel);

        this.add(jtp);
        this.setTitle("平板助手管理系统");
        this.setSize(1100, 660);
        this.setLocation(0, 0);
        //显示窗口true
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
