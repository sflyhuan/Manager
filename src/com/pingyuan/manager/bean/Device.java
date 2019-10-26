package com.pingyuan.manager.bean;

import com.pingyuan.manager.adb.DeviceState;

import javax.swing.table.DefaultTableModel;

public class Device extends DefaultTableModel {



    public Object[] rowData = {"sdadas",  "sadasd", "dsada", "asddasd"};
    public Device() {
        super();
        addColumn("Name");
        addColumn("Status");
        addColumn("Size");
        addColumn("Speed");
    }

    private String id;//设备ID
    private DeviceState deviceState;//类型
    private String androidVersion;
    private String androidModel;//安卓设备型号
    private String androidBrand;//安卓设备名称

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
