package com.pingyuan.manager.logs;

import com.pingyuan.manager.bean.FilePath;
import com.pingyuan.manager.bean.Log;
import com.pingyuan.manager.equipment.ButtonCellEditor;
import com.pingyuan.manager.equipment.ButtonRenderer;
import com.pingyuan.manager.users.CameraDialogPanel;
import com.pingyuan.manager.utils.LogDefaultTableModel;
import com.pingyuan.manager.utils.MsgManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class LogPanel extends JPanel {
    private List<Log> logList = new ArrayList<>();
    private JTable mTable = null;
    private LogDefaultTableModel mDefaultTableModel; //表格模型
    private JFrame mFrame;

    public LogPanel(JFrame jFrame) {
        this.mFrame = jFrame;
        this.setLayout(new BorderLayout());
        createJTable();
    }


    /**
     * 创建表格
     */

    private void createJTable() {
        mDefaultTableModel = new LogDefaultTableModel();
        mDefaultTableModel.addColumn("用户名");
        mDefaultTableModel.addColumn("设备ID");
        mDefaultTableModel.addColumn("时间");
        mDefaultTableModel.addColumn("事件");
        mDefaultTableModel.addColumn("");
        mDefaultTableModel.addColumn("");

        logList = LogModel.getLogList();
        for (Log log : logList) {
            addLogRow(log);
        }

        mTable = new JTable(mDefaultTableModel);
        mTable.setRowHeight(50);
        mTable.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        mTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mTable.setShowHorizontalLines(true);
        mTable.setShowVerticalLines(false);
        mTable.setRowSelectionAllowed(false);
        // 获取表头
        JTableHeader jTableHeader = mTable.getTableHeader();
        // 设置用户是否可以通过在头间拖动来调整各列的大小。
        jTableHeader.setResizingAllowed(false);
        // 设置用户是否可以拖动列头，以重新排序各列。
        jTableHeader.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) jTableHeader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        //设置单元格内容居中
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        mTable.getColumnModel().getColumn(0).setCellRenderer(render);
        mTable.getColumnModel().getColumn(1).setCellRenderer(render);
        mTable.getColumnModel().getColumn(2).setCellRenderer(render);
        mTable.getColumnModel().getColumn(3).setCellRenderer(render);

        mTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        mTable.getColumnModel().getColumn(4).setCellEditor(new ButtonCellEditor(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPhotoDialog(mFrame, mFrame);
            }
        }));

        mTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        mTable.getColumnModel().getColumn(5).setCellEditor(new ButtonCellEditor(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = mTable.getSelectedRow();
                LogModel.deleteLog(logList.get(selectedRow), new OnLogListener() {
                    @Override
                    public void delete() {
                        logList.remove(selectedRow);
                        mDefaultTableModel.removeRow(selectedRow);
                        MsgManager.showMsg("删除日志成功");
                    }
                });
            }
        }));

        this.add(mTable.getTableHeader(), BorderLayout.NORTH);
        this.add(new JScrollPane(mTable), BorderLayout.CENTER);
    }

    /**
     * 显示一个自定义的对话框
     *
     * @param owner           对话框的拥有者
     * @param parentComponent 对话框的父级组件
     */
    private void showPhotoDialog(Frame owner, Component parentComponent) {
        Log log = logList.get(mTable.getSelectedRow());
        String filePath = FilePath.getSingleton().getPullLogPath() + log.getDeviceID() + File.separator + "log/photos/" + log.getUserPicture();
        if (!new File(filePath).exists()) {
            MsgManager.showMsg("图片不存在");
            return;
        }
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "浏览", true);
        // 设置对话框的宽高
        dialog.setSize(500, 500);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(parentComponent);
        PhotoPanel photoPanel = new PhotoPanel(filePath);
        // 设置对话框的内容面板
        dialog.setContentPane(photoPanel);
        // 显示对话框
        dialog.setVisible(true);
    }


    /**
     * 添加日志
     *
     * @param log 日志
     */
    private void addLogRow(Log log) {
        Vector<Object> vector = new Vector<>();
        vector.add(log.getUserName());
        vector.add(log.getDeviceID());
        vector.add(log.getDate());
        vector.add(log.getAction());
        vector.add("/resources/default_user.jpeg");
        vector.add("/resources/delete.png");
        mDefaultTableModel.addRow(vector);
    }


    public interface OnLogListener {
        void delete();
    }
}





