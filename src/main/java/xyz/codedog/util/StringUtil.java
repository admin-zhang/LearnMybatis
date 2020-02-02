/**
 * Copyright (C), 2015-2020
 * FileName: StringUtil
 * Author:   Administrator
 * Date:     2020/2/2 10:47
 * Blog:     www.codedog.xyz
 */
package xyz.codedog.util;

public class StringUtil {
    public static boolean isEmpty(String str){
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
