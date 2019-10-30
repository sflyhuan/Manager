package com.pingyuan.manager.logs;

import com.pingyuan.manager.bean.Logs;
import com.pingyuan.manager.bean.User;
import com.pingyuan.manager.users.UserModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class LogsJPanel extends JPanel {
    private List<Logs> logsList = new ArrayList<>();
    private JTable jtable = null;
    private DefaultTableModel defaultTableModel; //表格模型

    public LogsJPanel(){
        this.setLayout(new BorderLayout());
        createJTable();
    }


    /**
     * 创建表格
     */

    private void createJTable() {


        defaultTableModel = new DefaultTableModel();

        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("用户名");
        defaultTableModel.addColumn("登陆日期");
        defaultTableModel.addColumn("行为");
        defaultTableModel.addColumn("头像");

        logsList  = LogsModel.getLogList();
        for (Logs logs : logsList) {
            Vector<Object> vector = new Vector<>();
            vector.add(logs.getUserID());
            vector.add(logs.getUserName());
            vector.add(logs.getDate());
            vector.add(logs.getAction());
            vector.add(logs.getUserPicture());
            defaultTableModel.addRow(vector);
        }

        jtable = new JTable(defaultTableModel);
        jtable.setRowHeight(50);


        jtable.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtable.setShowHorizontalLines(true);
        jtable.setShowVerticalLines(false);
        jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = jtable.getSelectedRow();
            }
        });
        this.add(jtable.getTableHeader(), BorderLayout.NORTH);
        this.add(jtable, BorderLayout.CENTER);

    }

}





