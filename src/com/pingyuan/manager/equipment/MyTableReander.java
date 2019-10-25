package com.pingyuan.manager.equipment;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyTableReander extends DefaultTableCellRenderer{
	
	private static final long serialVersionUID = 1L;

	/*�Ա�������Ⱦ��ʱ��Ԫ��Ĭ�Ϸ��ص���JLabel�����Ը�����Ҫ���ز�ͬ�Ŀؼ�*/
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean arg3, int row, int column) {
		// TODO Auto-generated method stub
		if (column==0) {
			JCheckBox box=new JCheckBox();
			box.setOpaque(true);
			setHorizontalAlignment(box.CENTER);
			box.setHorizontalAlignment(JCheckBox.CENTER);
			if (isSelected) {//�������ʱ��ı������еı���ɫ
				box.setBackground(new Color(135, 206, 250));
			} else {
				if (row % 2 == 0) {
					box.setBackground(new Color(240, 250, 250));
					box.setForeground(table.getForeground());
				} else {
					box.setBackground(table.getBackground());
				}
			}
			boolean valu=(Boolean) value;
			box.setSelected(valu);
			return box;
		}
		
		
		JLabel label = new JLabel();
		label.setOpaque(true);
		if (isSelected) {//�������ʱ��ı������еı���ɫ
			label.setBackground(new Color(135, 206, 250));
		} else {
			if (row % 2 == 0) {
				label.setBackground(new Color(240, 250, 250));
				label.setForeground(table.getForeground());
			} else {
				label.setBackground(table.getBackground());
			}
		}
		label.setText(value != null ? value.toString() : "");
		return label;
	}
}
