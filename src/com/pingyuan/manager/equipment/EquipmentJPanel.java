package com.pingyuan.manager.equipment;

import com.pingyuan.manager.bean.Device;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class EquipmentJPanel extends JPanel {
    public EquipmentJPanel() {
        this.setLayout(new BorderLayout());

        Device device=new Device();
        device.setId("dsajdnjas");
        device.setAndroidBrand("dmaksmkd");
        device.setAndroidVersion("skoadslmd");
        JTable table=new JTable(device);

        this.add(table, BorderLayout.CENTER);
    }
}

