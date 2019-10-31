package com.pingyuan.manager.utils;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JsoupManager {

    public static void saveDocument(File file, Document document){
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
