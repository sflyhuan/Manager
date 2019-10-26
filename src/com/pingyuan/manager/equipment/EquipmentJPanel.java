package com.pingyuan.manager.equipment;

import com.pingyuan.manager.bean.Device;
import com.pingyuan.manager.utils.ListUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class EquipmentJPanel extends JPanel {
    private final JTable table;

    public EquipmentJPanel() {
        this.setLayout(new BorderLayout());
        //TODO 开启搜索服务
        // 表头（列名）
        Object[] columnNames = {"设备ID", "名称", "型号", "状态", "上传", "取回"};
        List<Device> deviceList = new ArrayList<>();
        deviceList.add(new Device("设备ID", "20", "型号", "无", "上传", "取回"));
        deviceList.add(new Device("设备ID", "20", "型号", "无", "上传", "取回"));
        deviceList.add(new Device("设备ID", "20", "型号", "无", "上传", "取回"));
        deviceList.add(new Device("设备ID", "20", "型号", "无", "上传", "取回"));
        deviceList.add(new Device("设备ID", "20", "型号", "无", "上传", "取回"));
        deviceList.add(new Device("设备ID", "20", "型号", "无", "上传", "取回"));
        Object[][] data = toArray(deviceList);
        Device device = new Device(data, columnNames);

        table = new JTable(device);
        table.setRowSelectionAllowed(false);
        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonCellEditor(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        table.setValueAt("上传中...", row, 3);
                    }
                });
            }
        }));
        table.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonCellEditor(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                table.setValueAt("取回中...", row, 3);
            }
        }));


        this.add(table.getTableHeader(), BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);
    }

    public static <T> Object[][] toArray(List<T> data) {
        int size = data.size();
        Object[][] o = new Object[size][6];
        for (int i = 0; i < data.size(); i++) {
            Device device = (Device) data.get(i);
            o[i][0] = device.getId();
            o[i][1] = device.getAndroidBrand();
            o[i][2] = device.getAndroidModel();
            o[i][3] = device.getProgressIndex();
            o[i][4] = device.getUpdateButtonText();
            o[i][5] = device.getSaveButtonText();
        }
        return o;
    }
}

