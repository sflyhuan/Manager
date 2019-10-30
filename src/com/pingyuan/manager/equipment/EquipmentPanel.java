package com.pingyuan.manager.equipment;

import com.pingyuan.manager.adb.*;
import com.pingyuan.manager.bean.Device;
import com.pingyuan.manager.bean.FilePath;
import com.pingyuan.manager.test.TestMain;
import com.pingyuan.manager.utils.CustomDefaultTableModel;
import com.pingyuan.manager.utils.MsgManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class EquipmentPanel extends JPanel {
    List<Device> mDeviceList = new ArrayList<>();
    private JTable mTable = null;
    private CustomDefaultTableModel mDefaultTableModel;

    public EquipmentPanel() {
        this.setLayout(new BorderLayout());
        //创建表格
        createJTable();
        //开启ADB服务
        DeviceMonitorService.getSingleton().startMonitoringDevices(new DevicesWatchListener() {
            @Override
            public void findDevice(List<Device> deviceList) {
                mDeviceList.addAll(deviceList);
                for (Device device : deviceList) {
                    Vector<Object> vector = new Vector<>();
                    vector.add(device.getAndroidName());
                    vector.add(device.getId());
                    vector.add(device.getAndroidBrand());
                    vector.add(device.getAndroidModel());
                    vector.add(device.getEquipmentEnum().getName());
                    vector.add("/resources/push.png");
                    vector.add("/resources/pull.png");
                    mDefaultTableModel.addRow(vector);
                }
            }

            @Override
            public void detachDevice(List<Device> deviceList) {
                for (Device device : deviceList) {
                    for (int i = 0; i < mDeviceList.size(); i++) {
                        if (device.getId().equalsIgnoreCase(mDeviceList.get(i).getId())) {
                            if (device.getEquipmentEnum() == EquipmentEnum.UNKNOW) {
                                mDefaultTableModel.removeRow(i);
                            } else {
                                JOptionPane.showConfirmDialog(null, "设备" + device.getId() + "异常退出，传输已终止!", "警告", JOptionPane.ERROR_MESSAGE);
                                mDefaultTableModel.removeRow(i);
                            }
                        }
                    }
                }
                mDeviceList.removeAll(deviceList);
            }
        });
    }

    /**
     * 推送或取回数据
     *
     * @param equipmentEnum 设备
     */
    private void updateData(EquipmentEnum equipmentEnum) {
        int[] selectedRows = mTable.getSelectedRows();
        if (selectedRows.length > 0) {
            for (int selectedRow : selectedRows) {
                Device device = mDeviceList.get(selectedRow);
                if (device.getEquipmentEnum() != EquipmentEnum.UNKNOW) {
                    JOptionPane.showMessageDialog(null, "设备" + device.getId() + "非待机状态，请重新选择，请重新选择!", "注意设备非待机", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            int result = JOptionPane.showConfirmDialog(null, "是否对选择设备进行更新？", "已选择" + selectedRows.length + "个设备", JOptionPane.YES_NO_OPTION);
            if (result == 0) {
                //推送文件
                for (int selectedRow : selectedRows) {
                    Device device = mDeviceList.get(selectedRow);
                    device.setEquipmentEnum(equipmentEnum);
                    mDefaultTableModel.setValueAt(equipmentEnum.getName(), selectedRow, 4);
                    new Thread(() -> {
                        if (equipmentEnum == EquipmentEnum.PUSH) {
                            String[] from = new String[]{FilePath.getSingleton().getPushPath()};
                            String[] to = new String[]{FilePath.getSingleton().getDevicePushPath()};
                            ADBHelper.push(device.getId(), from, to, new ADBRunListener() {
                                @Override
                                public void onFinish(String response) {

                                }

                                @Override
                                public void onError(String msg) {
                                    if (device != null) {
                                        device.setEquipmentEnum(EquipmentEnum.ERROR);
                                        mDefaultTableModel.setValueAt(EquipmentEnum.ERROR.getName(), selectedRow, 4);
                                        MsgManager.showMsg(device.getId() + "推送数据异常，请重试");
                                    }
                                }

                                @Override
                                public void onFinish() {
                                    if (device != null) {
                                        device.setEquipmentEnum(EquipmentEnum.UNKNOW);
                                        mDefaultTableModel.setValueAt("推送完成", selectedRow, 4);
                                        MsgManager.showMsg(device.getId() + "推送完成");
                                    }
                                }
                            });
                        } else if (equipmentEnum == EquipmentEnum.PULL) {
                            String pullCameraPath = FilePath.getSingleton().getPullCameraPath() + File.separator + device.getId();
                            File pullCameraFile = new File(pullCameraPath);
                            if (pullCameraFile.exists()) {
                                pullCameraFile.mkdirs();
                            }
                            String pullLogPath = FilePath.getSingleton().getPullLogPath() + File.separator + device.getId();
                            File pullLogFile = new File(pullLogPath);
                            if (pullLogFile.exists()) {
                                pullLogFile.mkdirs();
                            }
                            String[] from = new String[]{
                                    FilePath.getSingleton().getDevicePullCameraPath(),
                                    FilePath.getSingleton().getDevicePullLogPath()
                            };
                            String[] to = new String[]{
                                    pullCameraPath,
                                    pullLogPath,
                            };

                            ADBHelper.pull(device.getId(), from, to, new ADBRunListener() {
                                @Override
                                public void onFinish(String response) {

                                }

                                @Override
                                public void onError(String msg) {
                                    if (device != null) {
                                        device.setEquipmentEnum(EquipmentEnum.ERROR);
                                        mDefaultTableModel.setValueAt(EquipmentEnum.ERROR.getName(), selectedRow, 4);
                                        MsgManager.showMsg(device.getId() + "取回数据异常，请重试");
                                    }
                                }

                                @Override
                                public void onFinish() {
                                    if (device != null) {
                                        device.setEquipmentEnum(EquipmentEnum.UNKNOW);
                                        mDefaultTableModel.setValueAt("取回完成", selectedRow, 4);
                                        MsgManager.showMsg(device.getId() + "取回完成");
                                    }
                                }
                            });
                        }
                    }).start();
                }
            }
        }
    }

    /**
     * 创建表格
     */
    private void createJTable() {
        mDefaultTableModel = new CustomDefaultTableModel();
        mDefaultTableModel.addColumn("名称");
        mDefaultTableModel.addColumn("设备ID");
        mDefaultTableModel.addColumn("平台");
        mDefaultTableModel.addColumn("型号");
        mDefaultTableModel.addColumn("状态");
        mDefaultTableModel.addColumn("");
        mDefaultTableModel.addColumn("");

        mTable = new JTable(mDefaultTableModel);
        mTable.setRowHeight(50);
        mTable.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        mTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mTable.setShowHorizontalLines(true);
        mTable.setShowVerticalLines(false);
        mTable.setRowSelectionAllowed(false);
        // 获取表头
        JTableHeader jTableHeader = mTable.getTableHeader();
        // 设置用户是否可以通过在头间拖动来调整各列的大小。
        jTableHeader.setResizingAllowed(false);
        // 设置用户是否可以拖动列头，以重新排序各列。
        jTableHeader.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) jTableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        mTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        mTable.getColumnModel().getColumn(5).setCellEditor(new ButtonCellEditor(e -> {
            updateData(EquipmentEnum.PUSH);
        }));
        mTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        mTable.getColumnModel().getColumn(6).setCellEditor(new ButtonCellEditor(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData(EquipmentEnum.PULL);
            }
        }));


        //设置单元格内容居中
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        mTable.getColumnModel().getColumn(0).setCellRenderer(render);
        mTable.getColumnModel().getColumn(1).setCellRenderer(render);
        mTable.getColumnModel().getColumn(2).setCellRenderer(render);
        mTable.getColumnModel().getColumn(3).setCellRenderer(render);
        mTable.getColumnModel().getColumn(4).setCellRenderer(render);

        this.add(mTable.getTableHeader(), BorderLayout.NORTH);
        this.add(new JScrollPane(mTable), BorderLayout.CENTER);
    }

}

