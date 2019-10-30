package com.pingyuan.manager.logs;

import com.pingyuan.manager.bean.Log;
import com.pingyuan.manager.utils.CustomDefaultTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class LogPanel extends JPanel {
    private List<Log> logList = new ArrayList<>();
    private JTable jtable = null;
    private CustomDefaultTableModel defaultTableModel; //表格模型

    public LogPanel(){
        this.setLayout(new BorderLayout());
        createJTable();
    }


    /**
     * 创建表格
     */

    private void createJTable() {
        defaultTableModel = new CustomDefaultTableModel();
        defaultTableModel.addColumn("用户名");
        defaultTableModel.addColumn("登陆日期");
        defaultTableModel.addColumn("行为");
        defaultTableModel.addColumn("头像");

        logList = LogModel.getLogList();
        for (Log log : logList) {
            addLogRow(log);
        }

        jtable = new JTable(defaultTableModel);
        jtable.setRowHeight(50);
        jtable.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jtable.setShowHorizontalLines(true);
        jtable.setShowVerticalLines(false);
        this.add(jtable.getTableHeader(), BorderLayout.NORTH);
        this.add(new JScrollPane(jtable), BorderLayout.CENTER);

    }

    /**
     * 添加日志
     * @param log 日志
     */
    private void addLogRow(Log log){
        Vector<Object> vector = new Vector<>();
        vector.add(log.getUserName());
        vector.add(log.getDate());
        vector.add(log.getAction());
        vector.add(log.getUserPicture());
        defaultTableModel.addRow(vector);
    }

}





