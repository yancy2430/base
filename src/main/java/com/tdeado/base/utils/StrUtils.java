package com.tdeado.base.utils;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StrUtils {

    /**
     * 参数转json
     * @param string
     * @return
     */
    public static String paramTojson(String string){
        Map<String,String> stringMap = new HashMap<String, String>();
        String[] strs = string.split("&");
        for (String str : strs) {
            String[] sts = new String[]{"",""};
            String[] st = str.split("=");
            if (st.length==1){
                sts[0] = st[0];
            }else {
                sts[0] = st[0];
                sts[1] = st[1];
            }
            stringMap.put(sts[0],sts[1]);
        }
        return new Gson().toJson(stringMap);
    }
    /**
     * bean转json
     * @param string
     * @return
     */
    public static String beanTojson(Object string){
        return new Gson().toJson(string);
    }
    /**
     * bean转参数
     * @param object
     * @return
     */
    public static String beanToParam(Object object){
        return jsonToParam(new Gson().toJson(object));
    }
    /**
     * json转参数
     * @param string
     * @return
     */
    public static String jsonToParam(String string){
        Map<String,String> map = new Gson().fromJson(string, Map.class);
        String str = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            str+=entry.getKey()+"="+entry.getValue()+"&";
        }
        str = str.substring(0,str.length()-1);
        return str;
    }
    /**
     * json转bean
     * @param string
     * @return
     */
    public static Object jsonTobean(String string,Class<?> t){
        return new Gson().fromJson(string,t);
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(long seconds,String format) {
        if(seconds == 0 ){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(seconds));
    }
    /**
     * 日期格式字符串转换成时间戳
     * @param date_str 字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     * @return
     */
    public static String timeStamp(){
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);
        return t;
    }

}
