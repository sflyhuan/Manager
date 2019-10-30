package com.pingyuan.manager.logs;

import javax.swing.*;
import java.awt.*;

public class PhotoPanel extends JPanel {
    public PhotoPanel(String facePath) {
        ImageIcon imageIcon=new ImageIcon(facePath);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
        add(new JLabel(imageIcon));
    }
}
