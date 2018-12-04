package com.tdeado.base;

import freemarker.template.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class generatingTest {
    private static final String projectPath = "/Users/yangzhe/gitee/Factory";
    private static final String packagePath = "/com/tdeado/factory";
    private static final String packageName = "com.tdeado.factory";

    public static void test(){

    }
    public static void main(String[] args){

        /**
        String tableName = "tb_answer";
        String className = "Answer";
        Map<String,Object> root = new HashMap<>();
        root.put("className",className);
        root.put("packageName",packageName);
        root.put("attrs",DatabaseUtil.getTableColumns(tableName));
        root.put("tableName",tableName);
        root.put("timeStamp",String.valueOf(System.currentTimeMillis()));
        root.put("tableAttrs",DatabaseUtil.getTableData(tableName));
        root.put("sense","`");

        System.err.println(root.get("attrs").toString());
        File fileT = new File(projectPath + "/src/main/resources/static/");//模板路径
        FileWriter out = null;
        // 通过FreeMarker的Confuguration读取相应的模板文件
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        // 设置默认字体
        configuration.setDefaultEncoding("utf-8");
        // 设置模板路径
        try {
            configuration.setDirectoryForTemplateLoading(fileT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //生成HTML
        Template template = null;
        try {
            template = configuration.getTemplate("html/list.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //设置输出文件
        File file = createNewFileName(projectPath + "/src/main/resources/templates/src/views/" +className + "/list.html");

        //设置输出流
        try {
            out = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //模板输出静态文件
        try {
            template.process(root, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

         **/
        try {
            String tableName = "tb_answer";
            String className = "Answer";
            Map<String,Object> root = new HashMap<>();
            root.put("className",className);
            root.put("packageName",packageName);
            root.put("attrs",DatabaseUtil.getTableColumns(tableName));
            root.put("tableName",tableName);
            root.put("timeStamp",String.valueOf(System.currentTimeMillis()));
            root.put("tableAttrs",DatabaseUtil.getTableData(tableName));
            root.put("sense","`");
            createBaseServiceFromModel(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 生成 代码
     * @param object
     */
    public static void createBaseServiceFromModel(Map<String,Object> object) throws Exception {
        File fileT = new File(projectPath + "/src/main/resources/static/");//模板路径
        FileWriter out = null;
        // 通过FreeMarker的Confuguration读取相应的模板文件
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        // 设置默认字体
        configuration.setDefaultEncoding("utf-8");
        // 设置模板路径
        configuration.setDirectoryForTemplateLoading(fileT);
        // 获取模板
        Template template = configuration.getTemplate("BaseService.ftl");
        //设置输出文件
        File file = createNewFileName(projectPath + "/src/main/java" + packagePath+"/service/base/Base"+object.get("className").toString()+"Service.java");
        //设置输出流
        out = new FileWriter(file);
        //模板输出静态文件
        template.process(object, out);
        //BaseServiceImpl
        configuration.setDirectoryForTemplateLoading(fileT);
        template = configuration.getTemplate("BaseServiceImpl.ftl");
        file = createNewFileName(projectPath + "/src/main/java" +  packagePath+"/service/base/impl/Base"+object.get("className").toString()+"ServiceImpl.java");
        out = new FileWriter(file);
        template.process(object, out);

        //BaseController
        configuration.setDirectoryForTemplateLoading(fileT);
        template = configuration.getTemplate("BaseController.ftl");
        file = createNewFileName(projectPath + "/src/main/java" +  packagePath+"/web/base/Base"+object.get("className").toString()+"Controller.java");
        out = new FileWriter(file);
        template.process(object, out);

        //生成entity
        template = configuration.getTemplate("entity.ftl");
        //设置输出文件
        file = createNewFileName(projectPath + "/src/main/java" + packagePath+"/entity/"+object.get("className").toString()+".java");
        //设置输出流
        out = new FileWriter(file);
        //模板输出静态文件
        template.process(object, out);


        //BaseMapper
        configuration.setDirectoryForTemplateLoading(fileT);
        template = configuration.getTemplate("BaseMapper.ftl");
        file = createNewFileName(projectPath + "/src/main/java" +  packagePath+"/dao/base/Base"+object.get("className").toString()+"Mapper.java");
        out = new FileWriter(file);
        template.process(object, out);

        //生成BaseMapper xml
        template = configuration.getTemplate("MapperXml.ftl");
        //设置输出文件
        file = createNewFileName(projectPath + "/src/main/resources/mapper/base/Base"+object.get("className").toString()+"Mapper.xml");
        //设置输出流
        out = new FileWriter(file);
        //模板输出静态文件
        template.process(object, out);

        /*
        //生成BaseEntity
        template = configuration.getTemplate("BaseEntity.ftl");
        //设置输出文件
        file = createNewFileName(projectPath + "/src/main/java" + packagePath+"/entity/base/Base"+object.get("className").toString()+".java");
        //设置输出流
        out = new FileWriter(file);
        //模板输出静态文件
        template.process(object, out);
        //生成Service
        configuration.setDirectoryForTemplateLoading(fileT);
        template = configuration.getTemplate("Service.ftl");
        file = createNewFileName(projectPath + "/src/main/java" +  packagePath+"/service/"+object.get("className").toString()+"Service.java");
        out = new FileWriter(file);
        template.process(object, out);

        //ServiceImpl
        configuration.setDirectoryForTemplateLoading(fileT);
        template = configuration.getTemplate("ServiceImpl.ftl");
        file = createNewFileName(projectPath + "/src/main/java" +  packagePath+"/service/impl/"+object.get("className").toString()+"ServiceImpl.java");
        out = new FileWriter(file);
        template.process(object, out);
        //Controller
        configuration.setDirectoryForTemplateLoading(fileT);
        template = configuration.getTemplate("Controller.ftl");
        file = createNewFileName(projectPath + "/src/main/java" +  packagePath+"/web/"+object.get("className").toString()+"Controller.java");
        out = new FileWriter(file);
        template.process(object, out);

        //Mapper
        configuration.setDirectoryForTemplateLoading(fileT);
        template = configuration.getTemplate("Mapper.ftl");
        file = createNewFileName(projectPath + "/src/main/java" +  packagePath+"/dao/"+object.get("className").toString()+"Mapper.java");
        out = new FileWriter(file);
        template.process(object, out);


        //生成xml
        template = configuration.getTemplate("MapperXmlEmpty.ftl");
        //设置输出文件
        file = createNewFileName(projectPath + "/src/main/resources/mapper/"+object.get("className").toString()+"Mapper.xml");
        //设置输出流
        out = new FileWriter(file);
        //模板输出静态文件
        template.process(object, out);

        //生成HTML
        template = configuration.getTemplate("html/list.ftl");
        //设置输出文件
        file = createNewFileName(projectPath + "/src/main/resources/templates/src/views/"+object.get("className").toString()+"/list.html");
        //设置输出流
        out = new FileWriter(file);
        //模板输出静态文件
        template.process(object, out);
         */

    }



    public static File createNewFileName(String strPath){
        File file = new File(strPath);
        File fileParent = file.getParentFile();
        if(!fileParent.exists()){
            fileParent.mkdirs();
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static String readToString(String fileName) {
        String encoding = "ISO-8859-1";
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
