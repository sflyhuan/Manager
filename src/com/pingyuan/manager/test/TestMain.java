package com.pingyuan.manager.test;

import javax.swing.*;

public class TestMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(200, 200);
        JLabel jLabel = new JLabel();
        ImageIcon image = new ImageIcon("C:\\Users\\Administrator\\IdeaProjects\\Manager\\src\\resources\\edit.png");
        jLabel.setIcon(image);
        frame.add(jLabel);
        frame.setVisible(true);
    }
}
