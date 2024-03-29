package com.pingyuan.manager;

import com.pingyuan.manager.main.MainFrame;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import javax.swing.plaf.InsetsUIResource;

public class
Main {

    public static final String SERVER_ROOT_PATH ="d://test//py-data";
    public static final String CLIENT_ROOT_PATH ="//sdcard//py-data";

    public static void main(String[] args) {
        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", false); //去掉设置按钮
            UIManager.put("TabbedPane.tabAreaInsets", new InsetsUIResource(3, 0, 2, 20));
            BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        new MainFrame();
    }

}
