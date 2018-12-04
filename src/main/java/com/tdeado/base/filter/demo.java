package com.tdeado.base.filter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tdeado.base.DatabaseUtil;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

public class demo {
    public static void main(String arg[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        String str = "{\n" +
                "\t\"id\": 1,\n" +
                "\t\"title\": \"用户管理\",\n" +
                "\t\"module\": \"user\",\n" +
                "\t\"control\": \"\",\n" +
                "\t\"action\": \"\",\n" +
                "\t\"isMenu\": 1,\n" +
                "\t\"status\": 1,\n" +
                "\t\"upid\": 0,\n" +
                "\t\"icon\": \"layui-icon-user\"\n" +
                "}";

        Object object = new Gson().fromJson(str,Class.forName("com.tdeado.base.entity.Access"));
        Type type = new TypeToken<Map<String,  DatabaseUtil.DBTb>>() {}.getType();
        Map<String, DatabaseUtil.DBTb> map = new Gson().fromJson(readToString("/Users/yangzhe/tbinfo.json"),type);
        DatabaseUtil.DBTb db = map.get("tb_access");

        Method[] f = object.getClass().getDeclaredMethods();
        for (Method method : f) {
            Class<?> clazz = object.getClass();
            if (method.getName().contains("get")){
                method = clazz.getMethod(method.getName());
                Object s1 = method.invoke(object);
            }
        }
    }
    public static String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

}
