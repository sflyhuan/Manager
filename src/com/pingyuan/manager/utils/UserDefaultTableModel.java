package com.pingyuan.manager.utils;

import javax.swing.table.DefaultTableModel;

public class UserDefaultTableModel extends DefaultTableModel {

    @Override
    public boolean isCellEditable(int row, int column) {
        if (column > 3) {
            return true;
        }
        return false;
    }
}
