package com.pingyuan.manager.equipment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumn;

public class TableTest extends JDialog{
	
	public TableTest(){
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(400, 300));
		this.setTitle("���DEMO");
		this.setVisible(true);
		this.pack();
		//����һ���������ű��
		JScrollPane jspanel = new JScrollPane(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JViewport view = new JViewport() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				setBackground(Color.WHITE);
			}
		};
		jspanel.setViewport(view);
		
		
		//������ŵ���������У������ͷ����ʾ
		Object[][] data={{false,"22","33","44"},{false,"22","33","44"},{false,"22","33","44"},{false,"22","33","44"}};
		MyTableModel model=new MyTableModel(data);
		JTable table=new JTable(model);
		
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setMinWidth(150);//����ÿһ�еĿ�ȵ���Сֵ
		}
		for (int i = 0; i < table.getColumnCount(); i++) {
			TableColumn cm1 = table.getColumnModel().getColumn(i);
			cm1.setCellRenderer(new MyTableReander());//Ϊ����ÿһ�н�����Ⱦ
		}
		
		TableColumn cm1 = table.getColumnModel().getColumn(0);
		cm1.setCellEditor(new MyTableCellEditor(new JCheckBox()));//��һ�з���JCheckBox�ؼ�
		
		table.setAutoResizeMode(table.AUTO_RESIZE_OFF);//�������ã����򲻳��ֹ�����
		jspanel.setViewportView(table);
		this.add(jspanel,BorderLayout.CENTER);
	}
	
	
	public static void main(String[] args) {
		new TableTest();
	}

}
