package com.pingyuan.manager.equipment;

import com.pingyuan.manager.bean.Device;
import com.pingyuan.manager.utils.ListUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class EquipmentJPanel extends JPanel {
    public EquipmentJPanel() {
        this.setLayout(new BorderLayout());
        //TODO 开启搜索服务
        // 表头（列名）
        Object[] columnNames = {"设备ID", "名称", "型号"};
        List<Device> deviceList = new ArrayList<>();
        deviceList.add(new Device("设备ID", "20", "型号"));
        deviceList.add(new Device("设备ID", "20", "型号"));
        deviceList.add(new Device("设备ID", "20", "型号"));
        deviceList.add(new Device("设备ID", "20", "型号"));
        deviceList.add(new Device("设备ID", "20", "型号"));
        deviceList.add(new Device("设备ID", "20", "型号"));
        String[][] data = ListUtils.listToArrayWay(deviceList);
        Device device = new Device(data, columnNames);

        JTable table = new JTable(device);
        table.setRowSelectionAllowed(false);

        table.getColumnModel().getColumn(0).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(0).setCellEditor(new ButtonCellEditor(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null,
                        "Do you want to add 1 to it?", "choose one",
                        JOptionPane.YES_NO_OPTION);
            }
        }));

        table.getColumnModel().getColumn(1).setCellRenderer(new ProgressCellRenderer());



        this.add(table.getTableHeader(), BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);
    }

}

