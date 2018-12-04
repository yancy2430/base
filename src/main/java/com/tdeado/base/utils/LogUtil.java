package com.tdeado.base.utils;

public class LogUtil {

    public static void err(String message){
        System.err.println(message);
    }
    public static void err(String key,String message){
        System.err.println("**********************"+key+"start**********************");
        System.err.println(message);
        System.err.println("**********************"+key+"end**********************");

    }
    public static void out(String message){
        System.out.println(message);
    }
    public static void out(String key,String message){
        System.out.println("**********************"+key+"start**********************");
        System.out.println(message);
        System.out.println("**********************"+key+"end**********************");
    }
}
