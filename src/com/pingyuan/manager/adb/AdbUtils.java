package com.pingyuan.manager.adb;

import com.pingyuan.manager.bean.Device;
import com.pingyuan.manager.utils.Logger;
import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AdbUtils {

    //创建线程池
    public static ExecutorService executor = Executors.newScheduledThreadPool(2);

    /**
     * 执行ADB命令
     *
     * @param deviceId 设备id
     * @param command  命令
     * @return
     */
    public static String executeADBCommand(String deviceId, String command) {
        return executeCommand(getAdbCommand(deviceId, command));
    }

    /**
     * 异步执行ADB
     *
     * @param string
     * @param listener
     */
    public static void runAsync(String deviceId, String string, ADBRunListener listener) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                String resporse = executeADBCommand(deviceId, string);

                if (listener != null) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFinish(resporse);
                        }
                    });
                }
            }
        });
    }


    /**
     * 执行单条命令
     *
     * @param command 命令
     * @return
     */
    private static String executeCommand(String command) {
        return executeCommand(command, null);
    }


    /**
     * 获取ADB命令
     *
     * @param deviceId 设备ID
     * @param command  命令
     * @return 命令
     */
    private static String getAdbCommand(String deviceId, String command) {
        if (command.startsWith("adb")) {
            command = command.replaceFirst("adb", "");
        }

        return AdbUtils.class.getResource("/resources/adb.exe").getPath()+" "
                + (deviceId != null ? "-s " + deviceId + " " : "") + command;
    }

    /**
     * 执行命令
     *
     * @param command  命令
     * @param commands 命令
     * @return
     */
    private static String executeCommand(String command, String[] commands) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {

            String[] envp = {};
            if (commands == null) {
                p = Runtime.getRuntime().exec(command, envp);
            } else {
                p = Runtime.getRuntime().exec(commands, envp);
            }

            p.waitFor(10, TimeUnit.SECONDS);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            boolean firstLine = true;
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (!firstLine) {
                    output.append("\n");
                }
                firstLine = false;
                output.append(line);
                // System.out.println(line);
            }

            reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            while ((line = reader.readLine()) != null) {
                if (!firstLine) {
                    output.append("\n");
                }
                firstLine = false;
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            output.append(e.getMessage());
        }

        String result = output.toString();

        if (false) {
            Logger.d("Run: " + command + "\n" + result);
        }

        return result;
    }
}
