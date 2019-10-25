package com.pingyuan.manager.equipment;


import com.pingyuan.manager.bean.Device;

import javax.swing.*;

public class DevicePanel extends JPanel {

    private Device mDevice;


    public DevicePanel(Device mDevice) {
        this.mDevice = mDevice;
        this.add(new JLabel(""));
    }
}
