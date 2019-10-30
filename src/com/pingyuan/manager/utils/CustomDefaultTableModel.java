package com.pingyuan.manager.utils;

import javax.swing.table.DefaultTableModel;

public class CustomDefaultTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column >= 5) {
            return false;
        }
        return true;
    }
}
