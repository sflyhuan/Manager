package com.pingyuan.manager.users;

import com.pingyuan.manager.adb.EquipmentEnum;
import com.pingyuan.manager.bean.FilePath;
import com.pingyuan.manager.bean.User;
import com.pingyuan.manager.equipment.ButtonCellEditor;
import com.pingyuan.manager.equipment.ButtonRenderer;
import com.pingyuan.manager.utils.CustomDefaultTableModel;
import com.pingyuan.manager.utils.JsoupManager;
import com.pingyuan.manager.utils.MsgManager;
import com.pingyuan.manager.utils.UserDefaultTableModel;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class UsersPanel extends JPanel {
    private List<User> mUserList = new ArrayList<>();
    private JTable mTable = null;
    private UserDefaultTableModel mDefaultTableModel; //表格模型
    private JFrame jFrame;

    public UsersPanel(JFrame jFrame) {
        this.jFrame = jFrame;
        this.setLayout(new BorderLayout());
        createAddButton();
        createJTable();
    }

    private void createAddButton() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton button = new JButton("新增用户");
        panel.add(button, BorderLayout.EAST);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCustomDialog(jFrame, jFrame, true);
            }
        });
        this.add(panel, BorderLayout.NORTH);
    }

    /**
     * 创建表格
     */
    private void createJTable() {
        mDefaultTableModel = new UserDefaultTableModel();
        mDefaultTableModel.addColumn("用户名");
        mDefaultTableModel.addColumn("登录ID");
        mDefaultTableModel.addColumn("用户类型");
        mDefaultTableModel.addColumn("用户专业");
        mDefaultTableModel.addColumn("");
        mDefaultTableModel.addColumn("");

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
                showCustomDialog(jFrame, jFrame, false);
            }
        }));
        mTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        mTable.getColumnModel().getColumn(5).setCellEditor(new ButtonCellEditor(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        }));

        mUserList = UserModel.getUserList();
        for (User user : mUserList) {
            addUserRow(user);
        }

        JScrollPane pane = new JScrollPane(mTable);
        this.add(pane, BorderLayout.CENTER);
    }

    /**
     * 添加用户
     *
     * @param user 用户
     */
    private void addUserRow(User user) {
        Vector<Object> vector = new Vector<>();
        vector.add(user.getUserName());
        vector.add(user.getLoginID());
        vector.add(user.getUserType().equalsIgnoreCase("1") ? "管理员" : "用户");
        vector.add(user.getUserProfession());
        vector.add("/resources/edit.png");
        vector.add("/resources/delete.png");
        mDefaultTableModel.addRow(vector);
    }

    /**
     * 显示一个自定义的对话框
     *
     * @param owner           对话框的拥有者
     * @param parentComponent 对话框的父级组件
     */
    private void showCustomDialog(Frame owner, Component parentComponent, boolean isAdd) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "注册新用户", true);
        // 设置对话框的宽高
        dialog.setSize(400, 600);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(parentComponent);

        // 设置对话框的内容面板
        dialog.setContentPane(new UserDialogPanel(owner, isAdd ? null : mUserList.get(mTable.getSelectedRow()), new UserListener() {

            @Override
            public void addSuccess(User user) {
                addUserRow(user);
                mUserList.add(user);
                MsgManager.showMsg("新增用户成功");
                dialog.dispose();
            }

            @Override
            public void updateSuccess(User user) {
                int selectedRow = mTable.getSelectedRow();
                addUserRow(user);
                mUserList.add(user);
                mDefaultTableModel.removeRow(selectedRow);
                mUserList.remove(selectedRow);
                MsgManager.showMsg("更新用户成功");
                dialog.dispose();
            }

            @Override
            public void updateFailed() {
                MsgManager.showMsg("更新用户异常，请重试");
                dialog.dispose();
            }

            @Override
            public void dispose() {
                dialog.dispose();
            }

            @Override
            public void addFailed() {
                MsgManager.showMsg("新增用户异常，请重试");
                dialog.dispose();
            }
        }));
        // 显示对话框
        dialog.setVisible(true);
    }

    /**
     * 删除用户
     */
    private void deleteUser() {
        int i = mTable.getSelectedRow();
        if (i == -1) {
            MsgManager.showMsg("请重新选择");
            return;
        }
        User user = mUserList.get(i);
        int result = JOptionPane.showConfirmDialog(null, "是否删除", "删除用户", JOptionPane.YES_NO_OPTION);
        if (result == 0) {
            boolean isSuccess = deleteUser(user.getUserID());
            if (isSuccess) {
                mDefaultTableModel.removeRow(i);
            } else {
                MsgManager.showMsg("删除用户失败，请重试");
            }
        }
    }

    /**
     * 根据用户ID删除用户
     *
     * @param id 用户ID
     */
    private boolean deleteUser(String id) {
        String pushUser = FilePath.getSingleton().getPushUserPath();
        File file = new File(pushUser);
        if (!file.exists()) {
            return false;
        }
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(file);
            Element rootElement = document.getRootElement();
            List<Element> elementList = rootElement.elements("user");
            if (elementList.size() > 0) {
                for (Element element : elementList) {
                    String userID = element.elementText("userID");
                    if (id.equalsIgnoreCase(userID)) {
                        rootElement.remove(element);
                    }
                }
            }
            JsoupManager.saveDocument(file, document);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return true;
    }

    public interface UserListener {
        void addSuccess(User user);

        void updateSuccess(User user);

        void updateFailed();

        void dispose();

        void addFailed();
    }
}