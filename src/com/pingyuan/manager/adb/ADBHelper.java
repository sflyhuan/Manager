package com.pingyuan.manager.adb;


import com.pingyuan.manager.utils.Logger;

public class ADBHelper {

    /**
     * 取回文件
     *
     * @param from
     * @param to
     * @return
     */
    public static void pull(String deviceId, String[] from, String[] to, ADBRunListener adbRunListener) {
        boolean isSuccess = true;
        String msg = null;
        if (from.length > 0) {
            for (int i = 0; i < from.length; i++) {
                String run = "pull " + from[i] + " " + to[i] + "";
                String result = AdbUtils.executeADBCommand(deviceId, run);
                Logger.d(result);
                if (!result.trim().contains("pulled")) {
                    msg = result;
                    isSuccess = false;
                }
            }
        }
        if (isSuccess) {
            adbRunListener.onFinish();
        } else {
            adbRunListener.onError(msg);
        }
    }

    /**
     * 取回文件
     *
     * @param from
     * @param to
     * @return
     */
    public static void push(String deviceId, String[] from, String[] to, ADBRunListener adbRunListener) {
        boolean isSuccess = true;
        String msg = null;
        if (from.length > 0) {
            for (int i = 0; i < from.length; i++) {
                String run = "push " + from[i] + " " + to[i] + "";
                String result = AdbUtils.executeADBCommand(deviceId, run);
                Logger.d(result);
                if (!result.trim().contains("pushed")) {
                    msg = result;
                    isSuccess = false;
                }
            }
        }
        if (isSuccess) {
            adbRunListener.onFinish();
        } else {
            adbRunListener.onError(msg);
        }
    }


    /**
     * 获取设备型号
     *
     * @param deviceId 设备Id
     * @return
     */
    public static String getAndroidModel(String deviceId) {
        String run = "shell getprop ro.product.model";
        return AdbUtils.executeADBCommand(deviceId, run);
    }


    /**
     * 获取安卓版本
     *
     * @param deviceId 设备Id
     * @return
     */
    public static String getAndroidVersion(String deviceId) {
        String run = "shell getprop ro.build.version.release";
        return AdbUtils.executeADBCommand(deviceId, run);
    }


    /**
     * 获取IP
     *
     * @param deviceId 设备Id
     * @return
     */
    public static String getAndroidIP(String deviceId) {
        String run = "adb shell ifconfig wlan0";
        return AdbUtils.executeADBCommand(deviceId, run);
    }

    /**
     * 获取设备品牌
     *
     * @param deviceId 设备Id
     * @return
     */
    public static String getAndroidBrand(String deviceId) {
        String run = "adb shell getprop ro.product.brand";
        return AdbUtils.executeADBCommand(deviceId, run);
    }

    /**
     * 获取设备名称
     *
     * @param deviceId 设备Id
     * @return
     */
    public static String getAndroidName(String deviceId) {
        String run = "adb shell getprop ro.product.name";
        return AdbUtils.executeADBCommand(deviceId, run);
    }

    public static String rm(String deviceId, String fileToDelete) {
        return AdbUtils.executeADBCommand(deviceId, "shell rm \"" + fileToDelete + "\"");
    }

    public static String sendIntent(String deviceId, IntentBroadcast intentBroadcast) {
        String command = "shell am " + intentBroadcast.activityManagerCommand +
                (!intentBroadcast.action.equals("") ? " -a " + intentBroadcast.action : "") +
                (!intentBroadcast.data.equals("") ? " -d " + intentBroadcast.data : "") +
                (!intentBroadcast.mimeType.equals("") ? " -t " + intentBroadcast.mimeType : "") +
                (!intentBroadcast.category.equals("") ? " -c " + intentBroadcast.category : "") +
                (!intentBroadcast.component.equals("") ? " -n " + intentBroadcast.component : "") +
                "";

        Logger.d(command);

        String result = AdbUtils.executeADBCommand(deviceId, command);

        if (!result.contains("Error:")) {
            result = null;
        }

        return result;
    }
}
