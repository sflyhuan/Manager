package com.pingyuan.manager.equipment;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer implements TableCellRenderer {

    JPanel mJPanel=new JPanel();

    public ButtonRenderer() {
        mJPanel.setLayout(new BorderLayout());
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        ImageIcon image = new ImageIcon("src\\resources\\edit.png");
        image.setImage(image.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        JLabel label=new JLabel();
        label.setIcon(image);
        mJPanel.add(label,BorderLayout.CENTER);
        return label;
    }

}