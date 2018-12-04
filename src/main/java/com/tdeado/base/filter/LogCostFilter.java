package com.tdeado.base.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

public class LogCostFilter implements Filter {
    private BodyReaderHttpServletRequestWrapper requestWrapper;
    @Override
    public void init(FilterConfig filterConfig) {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        
        if (servletRequest instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        }
        if (null == requestWrapper) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            requestWrapper.getReader();
            BufferedReader br = null;
            br = requestWrapper.getReader();
            String str, wholeStr = "";
            while ((str = br.readLine()) != null) {
                wholeStr += str;
            }
            String[] modules = requestWrapper.getServletPath().split("/");
//            filt(wholeStr, modules[2], modules[3]);

            filterChain.doFilter(requestWrapper, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    public static void filt(String wholeStr, String module, String action) {
        /**
        //通过反射实例化一个类
        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.tdeado.factory.entity." + module);
            //获取的json转为这个类实体
            Object object = new Gson().fromJson(wholeStr, clazz.newInstance().getClass());
            //获取数据表信息
            Map<String, DatabaseUtil.DBTb> map = new Gson().fromJson(readToString("/Users/yangzhe/tbinfo.json"), new TypeToken<Map<String, DatabaseUtil.DBTb>>() {
            }.getType());
            DatabaseUtil.DBTb db = map.get("tb_" + module.toLowerCase());//获取配置文件
            Method[] f = object.getClass().getDeclaredMethods();//获取所有的方法
            for (Method method : f) {
                Class<?> clazzs = object.getClass();
                if (method.getName().contains("get")) {
                    method = clazzs.getMethod(method.getName());
                    Object s1 = method.invoke(object);
                    //字段名
                    String field = method.getName().replace("get", "").toLowerCase();
                    if (db.getTbInfo().get(field).getIsNull().equals("1")) {
                        //选填字段
                        System.err.println("选填" + s1);
                    } else {
                        //必填字段
                        if (null == s1 || "".equals(s1)) {
                            throw new RuntimeException("字段" + field + "不能为空!");
                        }
                        System.err.println("必填" + s1);

                    }
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(module + "模块不存在");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        **/
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