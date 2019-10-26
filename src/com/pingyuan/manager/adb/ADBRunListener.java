package com.pingyuan.manager.adb;

public interface ADBRunListener {
    void onFinish(String response);

    void onError(String msg);

    void onFinish();
}