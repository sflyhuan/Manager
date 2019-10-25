package JTableDemo;

import com.pingyuan.manager.equipment.MyTableCellEditor;
import com.pingyuan.manager.equipment.MyTableModel;
import com.pingyuan.manager.equipment.MyTableReander;

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
		this.setTitle("表格DEMO");
		this.setVisible(true);
		this.pack();
		//创建一个滚动面板放表格
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


		//表格必须放到滚动面板中，否则表头不显示
		Object[][] data={{false,"22","33","44"},{false,"22","33","44"},{false,"22","33","44"},{false,"22","33","44"}};
		MyTableModel model=new MyTableModel(data);
		JTable table=new JTable(model);

		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setMinWidth(150);//设置每一列的宽度的最小值
		}
		for (int i = 0; i < table.getColumnCount(); i++) {
			TableColumn cm1 = table.getColumnModel().getColumn(i);
			cm1.setCellRenderer(new MyTableReander());//为表格的每一列进行渲染
		}

		TableColumn cm1 = table.getColumnModel().getColumn(0);
		cm1.setCellEditor(new MyTableCellEditor(new JCheckBox()));//第一列放置JCheckBox控件

		table.setAutoResizeMode(table.AUTO_RESIZE_OFF);//必须设置，否则不出现滚动条
		jspanel.setViewportView(table);
		this.add(jspanel,BorderLayout.CENTER);
	}


	public static void main(String[] args) {
		new TableTest();
	}

}
