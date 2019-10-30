package com.pingyuan.manager.logs;

import com.pingyuan.manager.bean.Logs;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LogsModel {
    public static List<Logs> getLogList() {
        //创建用户集合，读取用户xml文件
        List<Logs> logList = new ArrayList<>();
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(new File("src/com/pingyuan/manager/logs/logs.xml"));
            Element rootElement = document.getRootElement();
            List<Element> elementList = rootElement.elements("log");
            if (elementList.size() > 0) {
                for (Element element : elementList) {
                    String userID = element.elementText("userID");
                    String userName = element.elementText("userName");
                    String date = element.elementText("date");
                    String action = element.elementText("action");
                    String userPicture = element.elementText("userPicture");
                    logList.add(new Logs(userID, userName,date,action,userPicture));
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return logList;
    }
}
