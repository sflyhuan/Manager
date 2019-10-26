package com.pingyuan.manager.test;

import javax.swing.*;
import java.awt.*;

public class TestMain {
    public static void main(String[] args) throws InterruptedException {
        JFrame frame=new JFrame();
        frame.setSize(300,100);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        JProgressBar jProgressBar=new JProgressBar();
        jProgressBar.setIndeterminate(true);
        frame.add(jProgressBar,BorderLayout.CENTER);

    }
}
