package com.pingyuan.manager.equipment;

import com.pingyuan.manager.adb.DeviceMonitorService;
import com.pingyuan.manager.adb.DevicesWatchListener;
import com.pingyuan.manager.bean.Device;
import com.pingyuan.manager.logs.Logger;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class EquipmentJPanel extends JPanel {
 //   private Object[] columnNames = {"设备ID", "名称", "平台", "型号", "状态"};

    List<Device> mDeviceList = new ArrayList<>();
    private JTable mTable = null;
    private DefaultTableModel mDefaultTableModel;

    public EquipmentJPanel() {
        this.setLayout(new BorderLayout());
        createJTable();
        createButton();
        //监听
        DeviceMonitorService.getSingleton().startMonitoringDevices(new DevicesWatchListener() {
            @Override
            public void findDevice(List<Device> deviceList) {
                mDeviceList.addAll(deviceList);
                for (Device device : deviceList) {
                    Vector<Object> vector = new Vector<>();
                    vector.add(device.getId());
                    vector.add(device.getAndroidName());
                    vector.add(device.getAndroidBrand());
                    vector.add(device.getAndroidModel());
                    vector.add(device.getProgressText());
                    mDefaultTableModel.addRow(vector);
                }
            }

            @Override
            public void detachDevice(List<Device> deviceList) {
            }
        });
    }

    /**
     * 创建按钮
     */
    private void createButton() {
        JButton pushBtn = new JButton("更新");
        JButton pullBtn = new JButton("取回");
        JPanel jPanel = new JPanel();
        jPanel.add(pushBtn);
        jPanel.add(pullBtn);
        pushBtn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
        pullBtn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
        pushBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = mTable.getSelectedRows();
                Logger.e("更新" + selectedRows[0]);
            }
        });

        pullBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = mTable.getSelectedRows();
                Logger.e("取回" + selectedRows[0]);
            }
        });
        this.add(jPanel, BorderLayout.SOUTH);
    }

    /**
     * 创建表格
     */
    private void createJTable() {
        //dm4j du  xml  userList


        mDefaultTableModel = new DefaultTableModel();
        mDefaultTableModel.addColumn("设备ID");
        mDefaultTableModel.addColumn("名称");
        mDefaultTableModel.addColumn("平台");
        mDefaultTableModel.addColumn("型号");
        mDefaultTableModel.addColumn("状态");


        mTable = new JTable(mDefaultTableModel);
        mTable.setRowHeight(50);
        mTable.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        mTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        mTable.setShowHorizontalLines(true);
        mTable.setShowVerticalLines(false);
        mTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = mTable.getSelectedRow();
            }
        });
        this.add(mTable.getTableHeader(), BorderLayout.NORTH);
        this.add(mTable, BorderLayout.CENTER);
    }

}

