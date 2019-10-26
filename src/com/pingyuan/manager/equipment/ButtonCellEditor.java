package com.pingyuan.manager.equipment;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonCellEditor extends AbstractCellEditor implements TableCellEditor {

    private static final long serialVersionUID = -6546334664166791132L;
    private JPanel panel;
    private JButton button;
    Object value;

    public ButtonCellEditor(ActionListener actionListener) {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        button = new JButton();
        panel.add(this.button, BorderLayout.CENTER);
        button.addActionListener(actionListener);
    }


    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        this.value=value;
        button.setText(value == null ? "" : String.valueOf(value));
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return value;
    }
}