package com.pingyuan.manager.adb;

public enum EquipmentEnum {

    /**
     * 推送
     */
    PUSH("推送中"),
    /**
     * 取回
     */
    PULL("取回中"),
    /**
     * 无
     */
    UNKNOW("待机中"),
    /**
     * 无
     */
    ERROR("发生错误");


    private String name;

    EquipmentEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    }
