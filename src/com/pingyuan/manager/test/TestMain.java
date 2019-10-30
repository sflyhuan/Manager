package com.pingyuan.manager.test;

import com.github.sarxos.webcam.Webcam;
import com.pingyuan.manager.logs.Logger;
import net.samuelcampos.usbdrivedetector.USBDeviceDetectorManager;
import net.samuelcampos.usbdrivedetector.USBStorageDevice;
import net.samuelcampos.usbdrivedetector.events.IUSBDriveListener;
import net.samuelcampos.usbdrivedetector.events.USBStorageEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        USBDeviceDetectorManager driveDetector = new USBDeviceDetectorManager();
        driveDetector.getRemovableDevices().forEach(System.out::println);
        driveDetector.addDriveListener(new IUSBDriveListener() {
            @Override
            public void usbDriveEvent(USBStorageEvent usbStorageEvent) {
                USBStorageDevice storageDevice = usbStorageEvent.getStorageDevice();
                Logger.d(storageDevice.getDeviceName());
            }


        });
    }
}
