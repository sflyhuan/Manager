package com.pingyuan.manager.equipment;

import javax.swing.*;

public class EquipmentJPanel extends JPanel {
    public EquipmentJPanel() {
        JScrollPane scrollPane=new JScrollPane();    //创建滚动面板
        JList list=new JList();
        //限制只能选择一个元素
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(list);    //在滚动面板中显示列表
        String[] listData=new String[12];    //创建一个含有12个元素的数组
        for (int i=0;i<listData.length;i++)
        {
            listData[i]="这是列表框的第"+(i+1)+"个元素~";    //为数组中各个元素赋值
        }
        list.setListData(listData);    //为列表填充数据
    }
    }

