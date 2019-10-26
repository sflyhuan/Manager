package com.pingyuan.manager.bean;

import com.pingyuan.manager.adb.DeviceEnum;
import com.pingyuan.manager.adb.EquipmentEnum;

public class Device {

    private String id;//设备ID
    private String androidName;//安卓设备名称
    private String androidBrand;//安卓设备平台
    private String androidModel;//安卓设备型号
    private EquipmentEnum equipmentEnum = EquipmentEnum.UNKNOW;//设备状态
    private DeviceEnum deviceEnum;//类型

    public Device() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAndroidName() {
        return androidName;
    }

    public void setAndroidName(String androidName) {
        this.androidName = androidName;
    }

    public String getAndroidBrand() {
        return androidBrand;
    }

    public void setAndroidBrand(String androidBrand) {
        this.androidBrand = androidBrand;
    }

    public String getAndroidModel() {
        return androidModel;
    }

    public void setAndroidModel(String androidModel) {
        this.androidModel = androidModel;
    }

    public EquipmentEnum getEquipmentEnum() {
        return equipmentEnum;
    }

    public void setEquipmentEnum(EquipmentEnum equipmentEnum) {
        this.equipmentEnum = equipmentEnum;
    }

    public DeviceEnum getDeviceEnum() {
        return deviceEnum;
    }

    public void setDeviceEnum(DeviceEnum deviceEnum) {
        this.deviceEnum = deviceEnum;
    }
}
