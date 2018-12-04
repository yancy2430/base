package com.tdeado.base.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

public class ToKenUtils {

    private static String key="1383722663";
    public static String createToKen(){
        for (int i = 0; i <= 100; i++)
        {
            String sources = "0123456789abcdefghijklmnopqrstuvwxyz"; // 加上一些字母，就可以生成pc站的验证码了
            Random rand = new Random();
            StringBuffer flag = new StringBuffer();
            for (int j = 0; j < 6; j++)
            {
                flag.append(sources.charAt(rand.nextInt(9)) + "");
            }
            key = flag.toString();
        }
        key = getMD5(key+new Date().toString());
        return key;
    }

    public static void main(String[] args) {
        System.out.println(createToKen());
    }
    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
        }
        return "";
    }
}
