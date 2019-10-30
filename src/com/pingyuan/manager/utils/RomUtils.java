package com.pingyuan.manager.utils;

import java.util.HashMap;
import java.util.Map;

public class RomUtils {

    public static String getChineseName(String brand) {
        Map<String, String> brandHashMap = new HashMap();
        brandHashMap.put("huawei", "华为");
        brandHashMap.put("vivo", "VIVO");
        brandHashMap.put("xiaomi", "小米");
        brandHashMap.put("oppo", "OPPO");
        brandHashMap.put("leeco", "乐视");
        brandHashMap.put("letv", "乐视");
        brandHashMap.put("qiku", "360");
        brandHashMap.put("360", "360");
        brandHashMap.put("zte", "中兴");
        brandHashMap.put("oneplus", "一加");
        brandHashMap.put("nubia", "努比亚");
        brandHashMap.put("samsung", "三星");
        brandHashMap.put("meizu", "魅族");
        brandHashMap.put("lenovo", "联想");
        return brandHashMap.getOrDefault(brand.toLowerCase(), brand);
    }
}
