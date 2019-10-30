package com.pingyuan.manager.logs;

import com.pingyuan.manager.bean.FilePath;
import com.pingyuan.manager.bean.Log;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LogModel {
    public static List<Log> getLogList(String deviceId) {
        List<Log> logList = new ArrayList<>();
        String pullLogPath = FilePath.getSingleton().getPullLogPath() + deviceId + File.separator + "logs.xml";
        File file = new File(pullLogPath);
        if (!file.exists()) {
            return logList;
        }
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(file);
            Element rootElement = document.getRootElement();
            List<Element> elementList = rootElement.elements("log");
            if (elementList.size() > 0) {
                for (Element element : elementList) {
                    String userID = element.elementText("userID");
                    String userName = element.elementText("userName");
                    String date = element.elementText("date");
                    String action = element.elementText("action");
                    String userPicture = element.elementText("userPicture");
                    logList.add(new Log(userID, userName, date, action, userPicture));
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return logList;
    }

    public static List<Log> getLogList() {
        List<Log> logList = new ArrayList<>();
        String pullLogPath = FilePath.getSingleton().getPullLogPath();
        File file = new File(pullLogPath);
        if (!file.exists()) {
            return logList;
        } else {
            File[] files = file.listFiles(File::isDirectory);
            if (files != null && files.length > 0) {
                for (File logFile : files) {
                    String deviceID = logFile.getName();
                    logList.addAll(getLogList(deviceID));
                }
            }
        }
        return logList;
    }
}
