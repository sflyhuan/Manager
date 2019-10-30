package com.pingyuan.manager.users;

import com.pingyuan.manager.bean.User;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class UsersJPanel extends JPanel {
    private List<User> mUuserList = new ArrayList<>();
    private JTable mTable = null;
    private DefaultTableModel mDefaultTableModel; //表格模型
    private JFrame jFrame;

    public UsersJPanel(JFrame jFrame) {
        this.jFrame = jFrame;
        this.setLayout(new BorderLayout());
        createButton();
        createJTable();
    }

    /**
     * 创建表格
     */
    private void createJTable() {
        mDefaultTableModel = new DefaultTableModel();
        mDefaultTableModel.addColumn("ID");
        mDefaultTableModel.addColumn("用户名");
        mDefaultTableModel.addColumn("登录ID");
        mDefaultTableModel.addColumn("用户类型");
        mDefaultTableModel.addColumn("用户专业");

        mUuserList = UserModel.getUserList();
        for (User user : mUuserList) {
            Vector<Object> vector = new Vector<>();
            vector.add(user.getUserID());
            vector.add(user.getUserName());
            vector.add(user.getLoginID());
            vector.add(user.getUserType());
            vector.add(user.getUserProfession());
            mDefaultTableModel.addRow(vector);
        }

        mTable = new JTable(mDefaultTableModel);
        mTable.setRowHeight(50);
        mTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mTable.setShowHorizontalLines(true);
        mTable.setShowVerticalLines(false);
        this.add(mTable.getTableHeader(), BorderLayout.NORTH);
        JScrollPane pane=new JScrollPane(mTable);

        this.add(pane, BorderLayout.CENTER);
    }


    /**
     * 创建按钮
     */
    private void createButton() {
        JButton addBtn = new JButton("新增");
        JButton modBtn = new JButton("修改");
        JButton delBtn = new JButton("删除");
        JPanel jPanel = new JPanel();
        jPanel.add(addBtn);
        jPanel.add(modBtn);
        jPanel.add(delBtn);

        addBtn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
        modBtn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));
        delBtn.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.normal));

        // 增加用户
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCustomDialog(jFrame, jFrame,true);
            }
        });

        //修改用户
        modBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mTable.getSelectedRow()>0){
                    showCustomDialog(jFrame, jFrame,false);
                }
            }
        });

        //删除选中用户
        delBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove();
            }
        });
        this.add(jPanel, BorderLayout.SOUTH);

    }


    /**
     * 显示一个自定义的对话框
     *
     * @param owner           对话框的拥有者
     * @param parentComponent 对话框的父级组件
     */
    private void showCustomDialog(Frame owner, Component parentComponent,boolean isAdd) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "注册新用户", true);
        // 设置对话框的宽高
        dialog.setSize(400, 600);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(parentComponent);

        // 设置对话框的内容面板
        dialog.setContentPane(new UserDialogPanel(owner, isAdd?null:mUuserList.get(mTable.getSelectedRow()), new UserAddListener() {

            @Override
            public void addSuccess(User user) {
                Vector<Object> vector = new Vector<>();
                vector.add(user.getUserID());
                vector.add(user.getUserName());
                vector.add(user.getLoginID());
                vector.add(user.getUserType());
                vector.add(user.getUserProfession());
                mDefaultTableModel.addRow(vector);
                dialog.dispose();
            }

            @Override
            public void updateSuccess(User user) {
                int selectedRow = mTable.getSelectedRow();
                Vector<Object> vector = new Vector<>();
                vector.add(user.getUserID());
                vector.add(user.getUserName());
                vector.add(user.getLoginID());
                vector.add(user.getUserType());
                vector.add(user.getUserProfession());
                mDefaultTableModel.removeRow(selectedRow);
                mUuserList.remove(selectedRow);
                mDefaultTableModel.addRow(vector);
                mUuserList.add(user);
                dialog.dispose();

            }

            @Override
            public void updateFailed() {
                showMsg("更新用户异常，请重试");
                dialog.dispose();
            }

            @Override
            public void dispose() {
                dialog.dispose();
            }

            @Override
            public void addFailed() {
                showMsg("更新新增户异常，请重试");
                dialog.dispose();
            }
        }));
        // 显示对话框
        dialog.setVisible(true);
    }


    public void showMsg(String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                "消息",
                JOptionPane.INFORMATION_MESSAGE);
    }


    //删除
    private void remove() {
        int i = mTable.getSelectedRow();
        User user = mUuserList.get(i);
        int result = JOptionPane.showConfirmDialog(null, "是否删除", "删除用户", JOptionPane.YES_NO_OPTION);
        if (result == 0) {
            deleteUser(user.getUserID());
            mDefaultTableModel.removeRow(i);
        }
    }

    private void deleteUser(String id) {
        File file = new File("src/com/pingyuan/manager/users/users.xml");
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

            OutputFormat format = OutputFormat.createPrettyPrint();
            //设置编码格式
            format.setEncoding("UTF-8");
            try {
                XMLWriter writer = new XMLWriter(new FileWriter(file), format);
                //写入数据
                writer.write(document);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public interface UserAddListener {
        void addSuccess(User user);

        void updateSuccess(User user);

        void updateFailed();

        void dispose();

        void addFailed();
    }

}



