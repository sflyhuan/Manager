package com.pingyuan.manager.equipment;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends DefaultTableCellRenderer {

    public ButtonRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        ImageIcon image = new ImageIcon((String) value);
        image.setImage(image.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        return new JLabel(image);
    }

}