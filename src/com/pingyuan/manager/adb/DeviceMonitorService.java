package com.pingyuan.manager.adb;

import com.pingyuan.manager.bean.Device;
import com.pingyuan.manager.logs.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeviceMonitorService {

    //每个1秒执行一次
    private static final long INTERVAL_DURATION = 1000;
    //创建独立线程池
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    //是否工作中
    private boolean working;
    //设备列表
    private List<Device> mDeviceList = new ArrayList<>();
    //volatile防止重排序
    private static volatile DeviceMonitorService mSingleton;

    private DevicesWatchListener mDevicesWatchListener;

    //防止new创建
    private DeviceMonitorService() {
    }

    /**
     * synchronized 线程锁，避免多线程并非造成的问题,不放在方法上，是为了提高效率
     */
    public static DeviceMonitorService getSingleton() {
        if (mSingleton == null) {
            synchronized (DeviceMonitorService.class) {
                if (mSingleton == null) {
                    mSingleton = new DeviceMonitorService();
                }
            }
        }
        return mSingleton;
    }

    /**
     * 开启服务
     */
    public synchronized void startMonitoringDevices(DevicesWatchListener devicesWatchListener) {
        this.mDevicesWatchListener = devicesWatchListener;
        working = true;
        executor.execute(monitorRunnable);
    }

    /**
     * 终止服务
     */
    public synchronized void stopMonitoringDevices() {
        working = false;
    }

    /**
     * 结束服务
     */
    public synchronized void shutDown() {
        executor.shutdownNow();
    }

    private Runnable monitorRunnable = new Runnable() {

        @Override
        public void run() {
            while (working) {
                String result = AdbUtils.executeADBCommand(null, "devices -l");
                String[] deviceStrs = result.split("\n");
                //查找所有设备
                List<Device> devices = findDevices(deviceStrs);
                devices.remove(0);
                int devicesSize = devices.size();
                int deviceListSize = mDeviceList.size();
                if (devicesSize != deviceListSize) {
                    if (devicesSize > deviceListSize) {//增加设备
                        List<Device> newDevices = findNewDevices(devices);
                        if (newDevices.size() > 0) {
                            mDevicesWatchListener.findDevice(newDevices);
                            mDeviceList.addAll(newDevices);
                        }
                    } else {//移除设备
                        List<Device> detachDevices = findDetachDevices(devices);
                        if (detachDevices.size() > 0) {
                            mDevicesWatchListener.detachDevice(detachDevices);
                            mDeviceList.removeAll(detachDevices);
                        }
                    }
                }

                try {
                    Thread.sleep(INTERVAL_DURATION);
                } catch (InterruptedException ignored) {
                }
            }
            Logger.d("Someone stopped me!!!");
        }


    };

    /**
     * 查找所有设备
     */
    private List<Device> findDevices(String[] deviceStrs) {
        List<Device> devices = new ArrayList<>();
        for (String deviceStr : deviceStrs) {
            String[] deviceDescriptionSplit = deviceStr.split("\\s+");
            String deviceId = deviceDescriptionSplit[0];
            Device device = new Device();
            for (String descriptionTemp : deviceDescriptionSplit) {
                if (descriptionTemp.contains("model:")) {
                    String state = descriptionTemp.split("model:")[1];
                    device.setDeviceState(DeviceState.getDeviceState(state));
                }
            }
            device.setId(deviceId);
            devices.add(device);
        }
        return devices;
    }

    private synchronized List<Device> findNewDevices(List<Device> devices) {
        List<Device> tempDevices = new ArrayList<>();
        int size = mDeviceList.size();
        for (Device device : devices) {
            boolean checkDevice = checkDevice(mDeviceList, device);
            if (!checkDevice) {
                tempDevices.add(device);
            }
        }
        return tempDevices;
    }

    private synchronized List<Device> findDetachDevices(List<Device> devices) {
        List<Device> tempDevices = new ArrayList<>();
        for (Device device : mDeviceList) {
            boolean checkDevice = checkDevice(devices, device);
            if (!checkDevice) {
                tempDevices.add(device);
            }
        }
        return tempDevices;
    }

    private synchronized boolean checkDevice(List<Device> deviceList, Device device) {
        for (Device tempDevice : deviceList) {
            if (device.getId().equalsIgnoreCase(tempDevice.getId())) {
                return true;
            }
        }
        return false;
    }
}
