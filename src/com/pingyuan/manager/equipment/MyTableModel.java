package com.pingyuan.manager.equipment;

import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;
	private static final String[] columns={"�ֶ�һ","�ֶζ�","�ֶ���","�ֶ���"};//���е����ֶ�
	
	public MyTableModel(Object[][] data){
		super(data, columns);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		//��дisCellEditable�����������Ƿ���ԶԱ����б༭��Ҳ��������ĳ�л����У����Ա༭���߲����Ա༭
		return super.isCellEditable(row, column);
	}
	
	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		super.setValueAt(arg0, arg1, arg2);
	}

}
