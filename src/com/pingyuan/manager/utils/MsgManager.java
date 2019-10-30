package com.pingyuan.manager.utils;

import javax.swing.*;

public class MsgManager {

    public static void showMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg, "消息", JOptionPane.INFORMATION_MESSAGE);
    }
}
