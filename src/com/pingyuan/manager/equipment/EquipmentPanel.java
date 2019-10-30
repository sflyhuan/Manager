package com.pingyuan.manager.equipment;

import com.pingyuan.manager.Main;
import com.pingyuan.manager.adb.*;
import com.pingyuan.manager.bean.Device;
import com.pingyuan.manager.bean.FilePath;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class EquipmentPanel extends JPanel {
    private Object[] columnNames = {"设备ID", "名称", "平台", "型号", "状态"};

    List<Device> mDeviceList = new ArrayList<>();
    private JTable mTable = null;
    private DefaultTableModel mDefaultTableModel;

    public EquipmentPanel() {
        this.setLayout(new BorderLayout());
        //创建按钮
        createButton();
        //创建表格
        createJTable();
        //开启ADB服务
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
                    vector.add(device.getEquipmentEnum().getName());
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
                updateData(EquipmentEnum.PUSH);
            }
        });

        pullBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData(EquipmentEnum.PULL);
            }
        });
        this.add(jPanel, BorderLayout.SOUTH);
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
                                    }
                                }

                                @Override
                                public void onFinish() {
                                    if (device != null) {
                                        device.setEquipmentEnum(EquipmentEnum.UNKNOW);
                                        mDefaultTableModel.setValueAt(EquipmentEnum.UNKNOW.getName(), selectedRow, 4);
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
                                    }
                                }

                                @Override
                                public void onFinish() {
                                    if (device != null) {
                                        device.setEquipmentEnum(EquipmentEnum.UNKNOW);
                                        mDefaultTableModel.setValueAt(EquipmentEnum.UNKNOW.getName(), selectedRow, 4);
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
        this.add(new JScrollPane(mTable), BorderLayout.CENTER);
    }

}

