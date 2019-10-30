package com.pingyuan.manager.other;

import com.pingyuan.manager.bean.FilePath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class OtherPanel extends JPanel implements ActionListener {


    public OtherPanel() {
        addJButton("更新索引");
        addJButton("打开用户资料文件夹");
    }


    private void addJButton(String text) {
        Font f=new Font("宋体",Font.BOLD,20);//根据指定字体名称、样式和磅值大小，创建一个新 Font。
        JButton jButton = new JButton(text);
        Dimension preferredSize = new Dimension(300, 100);//设置尺寸
        jButton.setPreferredSize(preferredSize);
        jButton.setHorizontalAlignment(JButton.CENTER);
        jButton.setVerticalAlignment(JButton.CENTER);
        jButton.setFont(f);
        jButton.addActionListener(this);
        this.add(jButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "更新索引":

                break;
            case "打开资源文件夹":
                try {
                    Desktop.getDesktop().open(new File(FilePath.getSingleton().getPushCustomPath()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
