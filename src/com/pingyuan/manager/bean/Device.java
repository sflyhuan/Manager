package com.pingyuan.manager.bean;

import com.pingyuan.manager.adb.DeviceState;

import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public class Device extends DefaultTableModel {

    private String id;//设备ID
    private String androidName;//安卓设备名称
    private String androidBrand;//安卓设备平台
    private String androidModel;//安卓设备型号
    private String progressText;//进度

    private DeviceState deviceState;//类型

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public Device() {
    }

    public Device(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    public Device(String id, String androidName, String androidBrand, String androidModel, String progressText) {
        this.id = id;
        this.androidName = androidName;
        this.androidBrand = androidBrand;
        this.androidModel = androidModel;
        this.progressText = progressText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAndroidModel() {
        return androidModel;
    }

    public void setAndroidModel(String androidModel) {
        this.androidModel = androidModel;
    }

    public String getAndroidBrand() {
        return androidBrand;
    }

    public void setAndroidBrand(String androidBrand) {
        this.androidBrand = androidBrand;
    }

    public String getAndroidName() {
        return androidName;
    }

    public void setAndroidName(String androidName) {
        this.androidName = androidName;
    }

    public String getProgressText() {
        return progressText;
    }

    public void setProgressText(String progressText) {
        this.progressText = progressText;
    }

    public DeviceState getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(DeviceState deviceState) {
        this.deviceState = deviceState;
    }
}
