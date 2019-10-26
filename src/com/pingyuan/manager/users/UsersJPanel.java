package com.pingyuan.manager.users;

import com.pingyuan.manager.bean.User;
import com.pingyuan.manager.logs.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class UsersJPanel extends JPanel {
    //   private Object[] columnNames = {"用户ID", "用户名", "登录ID", "登录密码", "用户类型", "用户专业", "用户照片"};

    private List<User> mUuserList = new ArrayList<>();
    private JTable mTable = null;
    private DefaultTableModel mDefaultTableModel; //表格模型

    public UsersJPanel() {
        this.setLayout(new BorderLayout());
        createJTable();
        createButton();
    }

    /**
     * 创建表格
     */
    private void createJTable() {
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
        mTable.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        mTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mTable.setShowHorizontalLines(true);
        mTable.setShowVerticalLines(false);
        mTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = mTable.getSelectedRow();
            }
        });
        this.add(mTable.getTableHeader(), BorderLayout.NORTH);
        this.add(mTable, BorderLayout.CENTER);
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

        addBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = mTable.getSelectedRows();
                UserDialog jDialog = new UserDialog();
                jDialog.setVisible(true);
            }
        });


        modBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = mTable.getSelectedRows();
                Logger.e("修改" + selectedRows[0]);
                UserDialog jDialog = new UserDialog(mUuserList.get(selectedRows[0]));
                jDialog.setVisible(true);



            }
        });
        delBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = mTable.getSelectedRows();
                Logger.e("删除" + selectedRows[0]);
            }
        });
        this.add(jPanel, BorderLayout.SOUTH);
    }

    // 点击增加节点
    public void add() {

        List<User> userList = new ArrayList<>();
        SAXReader saxReader = new SAXReader();
        try {
            File file = new File("src/com/pingyuan/manager/users/users.xml");
            Document document = saxReader.read(file);
            Element rootElement = document.getRootElement();
            Element user = rootElement.addElement("user");
            user.addAttribute("userID", "");
            user.addAttribute("userName", "");
            user.addAttribute("loginID", "");
            user.addAttribute("loginPassword", "");
            user.addAttribute("userType", "");
            user.addAttribute("userProfession", "");
            user.addAttribute("userPicture", "");
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
}


