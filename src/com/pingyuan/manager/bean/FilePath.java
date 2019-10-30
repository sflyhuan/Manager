package com.pingyuan.manager.bean;

import java.util.ResourceBundle;

public class FilePath {

    //volatile防止重排序
    private static volatile FilePath mSingleton;

    private String pushPath;
    private String pullPath;
    private String pushFacePath;
    private String pushUserPath;
    private String pushCustomPath;
    private String pullCameraPath;
    private String pullLogPath;

    private String devicePushPath;
    private String devicePullCameraPath;
    private String devicePullLogPath;


    private FilePath() {
    }

    public static void main(String[] args) {
        FilePath singleton = getSingleton();
    }

    /**
     * synchronized 线程锁，避免多线程并非造成的问题,不放在方法上，是为了提高效率
     */
    public static FilePath getSingleton() {
        if (mSingleton == null) {
            synchronized (FilePath.class) {
                if (mSingleton == null) {
                    mSingleton = new FilePath();
                    ResourceBundle resource = ResourceBundle.getBundle("resources/app");
                    String data = resource.getString("data");
                    String push = data + resource.getString("push");
                    String pull = data + resource.getString("pull");
                    mSingleton.setPushPath(push);
                    mSingleton.setPullPath(pull);

                    mSingleton.setPushFacePath(push + resource.getString("push_face"));
                    mSingleton.setPushUserPath(push + resource.getString("push_user"));
                    mSingleton.setPushCustomPath(push + resource.getString("push_custom"));

                    mSingleton.setPullCameraPath(pull + resource.getString("pull_camera"));
                    mSingleton.setPullLogPath(pull + resource.getString("pull_log"));

                    mSingleton.setDevicePushPath( resource.getString("device_push"));
                    mSingleton.setDevicePullCameraPath( resource.getString("device_camera_pull"));
                    mSingleton.setDevicePullLogPath( resource.getString("device_log_pull"));

                }
            }
        }
        return mSingleton;
    }

    public String getPushPath() {
        return pushPath;
    }

    public void setPushPath(String pushPath) {
        this.pushPath = pushPath;
    }

    public String getPullPath() {
        return pullPath;
    }

    public void setPullPath(String pullPath) {
        this.pullPath = pullPath;
    }

    public String getPushFacePath() {
        return pushFacePath;
    }

    public void setPushFacePath(String pushFacePath) {
        this.pushFacePath = pushFacePath;
    }

    public String getPullCameraPath() {
        return pullCameraPath;
    }

    public void setPullCameraPath(String pullCameraPath) {
        this.pullCameraPath = pullCameraPath;
    }

    public String getPullLogPath() {
        return pullLogPath;
    }

    public void setPullLogPath(String pullLogPath) {
        this.pullLogPath = pullLogPath;
    }

    public String getPushUserPath() {
        return pushUserPath;
    }

    public void setPushUserPath(String pushUserPath) {
        this.pushUserPath = pushUserPath;
    }

    public String getPushCustomPath() {
        return pushCustomPath;
    }

    public void setPushCustomPath(String pushCustomPath) {
        this.pushCustomPath = pushCustomPath;
    }

    public String getDevicePushPath() {
        return devicePushPath;
    }

    public void setDevicePushPath(String devicePushPath) {
        this.devicePushPath = devicePushPath;
    }

    public String getDevicePullCameraPath() {
        return devicePullCameraPath;
    }

    public void setDevicePullCameraPath(String devicePullCameraPath) {
        this.devicePullCameraPath = devicePullCameraPath;
    }

    public String getDevicePullLogPath() {
        return devicePullLogPath;
    }

    public void setDevicePullLogPath(String devicePullLogPath) {
        this.devicePullLogPath = devicePullLogPath;
    }
}
