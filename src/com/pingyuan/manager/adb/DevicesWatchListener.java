package com.pingyuan.manager.adb;

import java.util.List;

public interface DevicesWatchListener {

    /**
     * 发现新的设备
     *
     * @param deviceList 设备
     */
    void findDevice(List<Device> deviceList);

    /**
     * 设备已移除
     *
     * @param deviceList 设备
     */
    void detachDevice(List<Device> deviceList);
}
