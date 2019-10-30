package com.pingyuan.manager.users;

import com.pingyuan.manager.bean.User;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UserDialogPanel extends JPanel {
    private final JLabel mImgLabel;
    private final User mUser;
    private String mUserPicture;
    private int maxUserID = 0;
    private Frame mFrame;


    public UserDialogPanel(Frame frame, User mUser, UsersJPanel.UserAddListener userAddListener) {
        this.mUser = mUser;
        this.mFrame = frame;
        this.setLayout(new BorderLayout());


        //===============用户信息===============
        JPanel userInfoPanel = new JPanel(new GridLayout(5, 1));
        //用户账号
        JPanel accountPanel = new JPanel();
        accountPanel.add(new JLabel("用户账号："));
        JTextField loginIDJTextField = new JTextField(12);
        accountPanel.add(loginIDJTextField);
        userInfoPanel.add(accountPanel);

        //密码
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("密    码："));
        JPasswordField passwordField = new JPasswordField(12);
        passwordPanel.add(passwordField);
        userInfoPanel.add(passwordPanel);

        //密码
        JPanel repeatPasswordPanel = new JPanel();
        repeatPasswordPanel.add(new JLabel("密    码："));
        JPasswordField repeatPasswordField = new JPasswordField(12);
        repeatPasswordPanel.add(repeatPasswordField);
        userInfoPanel.add(repeatPasswordPanel);

        // 姓名
        JPanel userNamePanel = new JPanel();
        userNamePanel.add(new JLabel("姓    名："));
        JTextField userNameTextField = new JTextField(12);
        userNamePanel.add(userNameTextField);
        userInfoPanel.add(userNamePanel);

        //用户专业
        JPanel professionPanel = new JPanel();
        professionPanel.add(new JLabel("用户专业："));
        String[] majors = {"机械", "军械", "特设", "电子"};
        final JComboBox comboBox = new JComboBox(majors);
        if (mUser != null) {
            for (int i = 0; i < majors.length; i++) {
                if (majors[i].equals(mUser.getUserProfession())) {
                    comboBox.setSelectedIndex(i);
                }
            }
        }
        professionPanel.add(comboBox);
        userInfoPanel.add(professionPanel);

        this.add(userInfoPanel, BorderLayout.NORTH);

        JPanel cameraPanel = new JPanel(new BorderLayout());
        JPanel btnCameraPanel = new JPanel();
        JButton cameraButton = new JButton("点击拍照");
        btnCameraPanel.add(cameraButton);
        cameraPanel.add(btnCameraPanel, BorderLayout.NORTH);

        JPanel imgPanel = new JPanel();
        mImgLabel = new JLabel();
        String path = "D:\\www_yanfa_5.png";
        if (mUser != null) {
            mUserPicture= mUser.getUserPicture();
            path = "D:\\" + mUser.getUserPicture() + ".png";
        }
        ImageIcon image = new ImageIcon(path);
        image.setImage(image.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
        mImgLabel.setIcon(image);
        imgPanel.add(mImgLabel);
        cameraPanel.add(imgPanel, BorderLayout.CENTER);
        this.add(cameraPanel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton okbutton = new JButton("确定");
        btnPanel.add(okbutton);
        this.add(btnPanel, BorderLayout.SOUTH);

        if (mUser != null) {
            loginIDJTextField.setText(mUser.getLoginID());
            passwordField.setText(mUser.getLoginPassword());
            repeatPasswordField.setText(mUser.getLoginPassword());
            userNameTextField.setText(mUser.getUserName());
        }

        cameraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String realName = userNameTextField.getText();
                if (realName == null || realName.equals("")) {
                    showMsg("姓名不能为空");
                    return;
                }
                String fileName = realName + "_" + "yanfa" + "_" + queryMaxUserId();
                showCustomDialog(mFrame, mFrame, fileName);
            }
        });

        okbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //更具Muser是否为null

                //获取用户输入的用户名
                String strLoginID = loginIDJTextField.getText();
                if (strLoginID == null || strLoginID.equals("")) {
                    showMsg("用户名不能为空");
                    return;
                }
                //获取用户名密码
                String strPwd = passwordField.getText();
                if (strPwd == null || strPwd.equals("")) {
                    showMsg("密码不能为空");
                    return;
                }
                String strRePwd = repeatPasswordField.getText();
                if (strRePwd == null || strRePwd.equals("")) {
                    showMsg("确认密码不能为空");
                    return;
                }
                String realName = userNameTextField.getText();
                if (realName == null || realName.equals("")) {
                    showMsg("姓名不能为空");
                    return;
                }
                //判断确认密码是否跟密码相同
                if (!strRePwd.equals(strPwd)) {
                    showMsg("确认密码跟密码不同");
                    return;
                }
                if (null == mUserPicture || mUserPicture.equals("")) {
                    showMsg("请采集头像");
                }

                String profession = comboBox.getSelectedItem().toString();
                if (mUser == null) {
                    User user = add(realName, strLoginID, strPwd, profession, mUserPicture);
                    if (user != null) {
                        userAddListener.addSuccess(user);
                    }else {
                        userAddListener.addFailed();
                    }
                } else {
                    if (!realName.equalsIgnoreCase(mUser.getUserName()) ||
                            !strLoginID.equalsIgnoreCase(mUser.getLoginID()) ||
                            !strPwd.equalsIgnoreCase(mUser.getLoginPassword()) ||
                            !profession.equalsIgnoreCase(mUser.getUserProfession()) ||
                            !mUserPicture.equalsIgnoreCase(mUser.getUserPicture())) {
                        User user = updateUser(realName, strLoginID, strPwd, profession, mUserPicture);
                        if (user != null) {
                            userAddListener.updateSuccess(user);
                        } else {
                            userAddListener.updateFailed();
                        }
                    }else {
                        userAddListener.dispose();
                    }
                }

            }

        });
    }

    private User updateUser(String realName, String strLoginID, String strPwd, String profession, String userPicture) {

        SAXReader saxReader = new SAXReader();
        try {
            File file = new File("src/com/pingyuan/manager/users/users.xml");
            Document document = saxReader.read(file);
            Element rootElement = document.getRootElement();
            List<Element> elementList = rootElement.elements("user");
            if (elementList.size() > 0) {
                for (Element element : elementList) {
                    String userID = element.elementText("userID");
                    if (userID.equalsIgnoreCase(mUser.getUserID())) {
                        if (!realName.equalsIgnoreCase(mUser.getUserName())) {
                            element.element("userName").setText(realName);
                            mUser.setUserName(realName);
                        }

                        if (!strLoginID.equalsIgnoreCase(mUser.getLoginID())) {
                            element.element("loginID").setText(strLoginID);
                            mUser.setLoginID(strLoginID);
                        }

                        if (!strPwd.equalsIgnoreCase(mUser.getLoginPassword())) {
                            element.element("loginPassword").setText(strPwd);
                            mUser.setLoginPassword(strPwd);
                        }
                        if (!profession.equalsIgnoreCase(mUser.getUserProfession())) {
                            element.element("userProfession").setText(profession);
                            mUser.setUserProfession(profession);
                        }
                        if (!userPicture.equalsIgnoreCase(mUser.getUserPicture())) {
                            element.element("userPicture").setText(userPicture);
                            mUser.setUserPicture(userPicture);
                        }

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
            return mUser;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void showMsg(String msg) {
        JOptionPane.showMessageDialog(
                null,
                msg,
                "消息",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // 点击增加用户节点
    public User add(String userName, String loginID, String loginPassword, String userProfession,
                    String userPicture) {
        String maxUserId = queryMaxUserId() + "";
        List<User> userList = new ArrayList<>();
        SAXReader saxReader = new SAXReader();
        try {
            File file = new File("src/com/pingyuan/manager/users/users.xml");
            Document document = saxReader.read(file);
            Element rootElement = document.getRootElement();
            Element user = rootElement.addElement("user");

            //查最大的用户ID
            Element userID = user.addElement("userID");
            userID.setText(queryMaxUserId() + "");

            Element userName1 = user.addElement("userName");
            userName1.setText(userName);

            Element loginID1 = user.addElement("loginID");
            loginID1.setText(loginID);

            Element loginPassword1 = user.addElement("loginPassword");
            loginPassword1.setText(loginPassword);

            Element userType = user.addElement("userType");
            userType.setText("0");

            Element userProfession1 = user.addElement("userProfession");
            userProfession1.setText(userProfession);


            Element userPicture1 = user.addElement("userPicture");
            userPicture1.setText(userPicture);

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
            return new User(maxUserId, userName, loginID, loginPassword, "0", userProfession,
                    userPicture);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int queryMaxUserId() {
        List<User> userList = UserModel.getUserList();
        for (User user : userList) {
            String userID = user.getUserID();
            maxUserID = Math.max(maxUserID, Integer.parseInt(userID));
        }
        return ++maxUserID;

    }

    /**
     * 显示一个自定义的对话框
     *
     * @param owner           对话框的拥有者
     * @param parentComponent 对话框的父级组件
     */
    private void showCustomDialog(Frame owner, Component parentComponent, String cameraName) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "注册新用户", true);
        // 设置对话框的宽高
        dialog.setSize(500, 600);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(parentComponent);
        CameraDialogPanel cameraDialogPanel = new CameraDialogPanel(cameraName, new CameraOpenListener() {

            @Override
            public void open(String path, String userPicture) {
                ImageIcon image = new ImageIcon(path);
                image.setImage(image.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT));
                mImgLabel.setIcon(image);
                mUserPicture = userPicture;
                dialog.dispose();
            }
        });
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (null != cameraDialogPanel) {
                    cameraDialogPanel.getPanel().stop();
                }
                super.windowClosing(e);
            }
        });
        // 设置对话框的内容面板
        dialog.setContentPane(cameraDialogPanel);
        // 显示对话框
        dialog.setVisible(true);
    }


    public interface CameraOpenListener {
        void open(String path, String filePath);
    }
}