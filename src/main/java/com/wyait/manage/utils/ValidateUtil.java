package com.wyait.manage.utils;

import java.util.regex.Pattern;

public class ValidateUtil {
    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isUpperCase = false;//定义一个boolean值，用来表示是否包含大写字母
        boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含小写字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit( str.charAt( i ) )) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isUpperCase( str.charAt( i ) )) {  //用char包装类中的判断字母的方法判断每一个字符
                isUpperCase = true;
            } else if (Character.isLowerCase( str.charAt( i ) )) {  //用char包装类中的判断字母的方法判断每一个字符
                isLowerCase = true;
            }
        }
        String regex = "^[a-zA-Z0-9]{6,12}$";
        boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches( regex );
        return isRight;
    }

    /**
     * 71      * 校验手机号
     * 72      *
     * 73      * @param mobile
     * 74      * @return 校验通过返回true，否则返回false
     * 75
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches( REGEX_MOBILE, mobile );
    }


}
