package com.pingyuan.manager.equipment;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ProgressCellRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;
    private JProgressBar b;

    public ProgressCellRenderer() {
        super();
        setOpaque(true);
        b = new JProgressBar();
        //是否绘制边框
        b.setBorderPainted(true);
        b.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        return b;
    }


}
