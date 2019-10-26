package com.pingyuan.manager.equipment;

import com.pingyuan.manager.bean.Device;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.lang.reflect.Field;


public class EquipmentJPanel extends JPanel {

    public EquipmentJPanel() {

        JTable table = new JTable(5,13);
      //  table.setFillsViewportHeight(true);
      //  table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        Device devices = new Device();



        this.add(table);



        JButton button1 = new JButton();
        JButton button2 = new JButton();
        button1.setBounds(300,300,300,300);
        button1.setText("确定");
        button2.setText("取消");
        button1.setBounds(400,400,400,400);
        this.add(button1);
        this.add(button2);

    }
}

