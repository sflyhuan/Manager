package com.pingyuan.manager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseUtil {

    private static Pattern pattern = Pattern.compile("[<>&'\"\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]");

    /**
     * 判断字符串是否纯英文
     *
     * @param str
     * @return
     */
    public static boolean isEnglish(String str) {
        byte[] bytes = str.getBytes();
        int i = bytes.length;// i为字节长度
        int j = str.length();// j为字符长度
        boolean result = i == j ? true : false;
        return result;
    }


    public static void main(String[] args) {
        Logger.d(isLetterDigit("123qweASD") + "sad");
    }


    public static String match(String value) {
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 包含大小写字母及数字且在6-12位
     *
     * @return
     */
    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]{6,12}$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }
}
