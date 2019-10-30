package com.pingyuan.manager.equipment;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {

    private Object mValue;
    private JButton mJButton;

    public ButtonCellEditor(ActionListener actionListener) {
        mJButton = new JButton();
        mJButton.addActionListener(actionListener);
    }


    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        mValue=value;
        ImageIcon image = new ImageIcon(ButtonRenderer.class.getResource(String.valueOf(value)));
        image.setImage(image.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        mJButton.setIcon(image);
        return mJButton;
    }

    @Override
    public Object getCellEditorValue() {
        return mValue;
    }
}