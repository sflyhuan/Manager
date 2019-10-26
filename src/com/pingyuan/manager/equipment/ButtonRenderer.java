package com.pingyuan.manager.equipment;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer implements TableCellRenderer {
    private JPanel panel;

    private JButton button;

    public ButtonRenderer() {
        button = new JButton();
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(button, BorderLayout.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        button.setText(value == null ? "" : String.valueOf(value));
        return panel;
    }

}