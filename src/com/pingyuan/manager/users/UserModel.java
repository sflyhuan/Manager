package com.pingyuan.manager.users;

import com.pingyuan.manager.bean.FilePath;
import com.pingyuan.manager.bean.User;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {

    public static List<User> getUserList() {
        //创建用户集合，读取用户xml文件
        List<User> userList = new ArrayList<>();
        String pushUser = FilePath.getSingleton().getPushUserPath();
        File file = new File(pushUser);
        if (!file.exists()) {
            return userList;
        }
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(file);
            Element rootElement = document.getRootElement();
            List<Element> elementList = rootElement.elements("user");
            if (elementList.size() > 0) {
                for (Element element : elementList) {
                    String userID = element.elementText("userID");
                    String userName = element.elementText("userName");
                    String loginID = element.elementText("loginID");
                    String loginPassword = element.elementText("loginPassword");
                    String userType = element.elementText("userType");
                    String userProfession = element.elementText("userProfession");
                    String userPicture = element.elementText("userPicture");
                    userList.add(new User(userID, userName, loginID, loginPassword, userType, userProfession, userPicture));
                }
            }
            UserModel.saveDocument(file,document);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static void saveDocument(File file,Document document){
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        try {
            XMLWriter writer = new XMLWriter(new FileWriter(file), format);
            //写入数据
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
