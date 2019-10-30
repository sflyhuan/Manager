package com.pingyuan.manager.users;

import com.pingyuan.manager.bean.FilePath;
import com.pingyuan.manager.bean.User;
import com.pingyuan.manager.utils.ChineseUtil;
import com.pingyuan.manager.utils.MsgManager;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;


public class UserDialogPanel extends JPanel {
    private final JLabel mImgLabel;
    private final User mUser;
    private String mUserPicture;
    private int maxUserID = 0;
    private Frame mFrame;


    public UserDialogPanel(Frame frame, User mUser, UsersPanel.UserListener userListener) {
        this.mUser = mUser;
        this.mFrame = frame;
        this.setLayout(new BorderLayout());


        //===============用户信息===============
        JPanel userInfoPanel = new JPanel(new GridLayout(5, 1));
        //用户账号
        JPanel accountPanel = new JPanel();
        accountPanel.add(new JLabel("用户账号："));
        JTextField accountTextField = new JTextField(12);
        accountPanel.add(accountTextField);
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
        String path = "src/com/pingyuan/manager/users/default_user.jpeg";

        if (mUser != null) {
            mUserPicture = mUser.getUserPicture();
            path = FilePath.getSingleton().getPushFacePath() + mUser.getUserPicture() + ".png";
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
            accountTextField.setText(mUser.getLoginID());
            passwordField.setText(mUser.getLoginPassword());
            repeatPasswordField.setText(mUser.getLoginPassword());
            userNameTextField.setText(mUser.getUserName());
        }

        cameraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountTextField.getText();
                if (account == null || account.equals("")) {
                    MsgManager.showMsg("用户账号不能为空");
                    return;
                } else {
                    if (!ChineseUtil.isEnglish(account)) {
                        MsgManager.showMsg("用户账号必须为英文");
                        return;
                    }
                    String match = ChineseUtil.match(account);
                    if (match != null) {
                        MsgManager.showMsg("用户账号中不能包含非法字符" + match);
                        return;
                    }
                }
                String fileName = account + "_" + account + "_" + queryMaxUserId();
                showCustomDialog(mFrame, mFrame, fileName);
            }
        });

        okbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取用户输入的用户名
                String account = accountTextField.getText();
                if (account == null || account.equals("")) {
                    MsgManager.showMsg("用户账号不能为空");
                    return;
                } else {
                    if (!ChineseUtil.isEnglish(account)) {
                        MsgManager.showMsg("用户账号必须为英文");
                        return;
                    }
                    String match = ChineseUtil.match(account);
                    if (match != null) {
                        MsgManager.showMsg("用户账号中不能包含非法字符" + match);
                        return;
                    }
                }

                //获取用户名密码
                String strPwd = passwordField.getText();
                if (strPwd == null || strPwd.equals("")) {
                    MsgManager.showMsg("密码不能为空");
                    return;
                } else {
                    if (!ChineseUtil.isLetterDigit(strPwd)) {
                        MsgManager.showMsg("密码必须包含大小写字母及数字且在6-12位");
                        return;
                    }
                    String match = ChineseUtil.match(strPwd);
                    if (match != null) {
                        MsgManager.showMsg("密码中不能包含非法字符" + match);
                        return;
                    }
                }
                String strRePwd = repeatPasswordField.getText();
                if (strRePwd == null || strRePwd.equals("")) {
                    MsgManager.showMsg("确认密码不能为空");
                    return;
                } else {
                    String match = ChineseUtil.match(strRePwd);
                    if (match != null) {
                        MsgManager.showMsg("确认密码中不能包含非法字符" + match);
                        return;
                    }
                }
                String realName = userNameTextField.getText();
                if (realName == null || realName.equals("")) {
                    MsgManager.showMsg("姓名不能为空");
                    return;
                } else {
                    String match = ChineseUtil.match(realName);
                    if (match != null) {
                        MsgManager.showMsg("姓名中不能包含非法字符" + match);
                        return;
                    }
                }
                //判断确认密码是否跟密码相同
                if (!strRePwd.equals(strPwd)) {
                    MsgManager.showMsg("确认密码跟密码不同");
                    return;
                }

                if (null == mUserPicture || mUserPicture.equals("")) {
                    MsgManager.showMsg("请采集头像");
                    return;
                }

                String profession = comboBox.getSelectedItem().toString();
                if (mUser == null) {
                    User user = add(realName, account, strPwd, profession, mUserPicture);
                    if (user != null) {
                        userListener.addSuccess(user);
                    } else {
                        userListener.addFailed();
                    }
                } else {
                    //判断用户信息是否修改
                    if (!realName.equalsIgnoreCase(mUser.getUserName()) ||
                            !account.equalsIgnoreCase(mUser.getLoginID()) ||
                            !strPwd.equalsIgnoreCase(mUser.getLoginPassword()) ||
                            !profession.equalsIgnoreCase(mUser.getUserProfession()) ||
                            !mUserPicture.equalsIgnoreCase(mUser.getUserPicture())) {
                        User user = updateUser(realName, account, strPwd, profession, mUserPicture);
                        if (user != null) {
                            userListener.updateSuccess(user);
                        } else {
                            userListener.updateFailed();
                        }
                    } else {
                        userListener.dispose();
                    }
                }

            }

        });
    }

    private User updateUser(String realName, String strLoginID, String strPwd, String profession, String userPicture) {
        SAXReader saxReader = new SAXReader();
        try {
            String pushUser = FilePath.getSingleton().getPushUserPath();
            File file = new File(pushUser);
            if (!file.exists()) {
                return null;
            }
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
            UserModel.saveDocument(file, document);
            return mUser;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 点击增加用户节点
    public User add(String userName, String loginID, String loginPassword, String userProfession,
                    String userPicture) {
        String pushUser = FilePath.getSingleton().getPushUserPath();
        File file = new File(pushUser);
        if (!file.exists()) {
            return null;
        }
        String maxUserId = queryMaxUserId() + "";
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(file);
            Element rootElement = document.getRootElement();
            Element user = rootElement.addElement("user");

            user.addElement("userID").setText(queryMaxUserId() + "");
            user.addElement("userName").setText(userName);
            user.addElement("loginID").setText(loginID);
            user.addElement("loginPassword").setText(loginPassword);
            user.addElement("userType").setText("0");
            user.addElement("userProfession").setText(userProfession);
            user.addElement("userPicture").setText(userPicture);

            UserModel.saveDocument(file, document);
            return new User(maxUserId, userName, loginID, loginPassword, "0", userProfession,
                    userPicture);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询最大用户ID
     */
    private int queryMaxUserId() {
        maxUserID = 0;
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
                cameraDialogPanel.getPanel().stop();
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