package com.pingyuan.manager.bean;

import com.pingyuan.manager.adb.DeviceState;

import javax.swing.table.DefaultTableModel;
import java.util.Objects;

public class Device extends DefaultTableModel {

    private String id;//设备ID
    private String androidModel;//安卓设备型号
    private String androidBrand;//安卓设备名称
    private DeviceState deviceState;//类型
    private String androidVersion;//Android版本

    public Device() {
    }

    public Device(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    public Device(String id, String androidModel, String androidBrand) {
        this.id = id;
        this.androidModel = androidModel;
        this.androidBrand = androidBrand;
    }



    public Device() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DeviceState getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(DeviceState deviceState) {
        this.deviceState = deviceState;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
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
}
