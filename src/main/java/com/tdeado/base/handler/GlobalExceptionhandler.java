package com.tdeado.base.handler;

import com.google.gson.JsonSyntaxException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionhandler {

    @ExceptionHandler(value = JsonSyntaxException.class)
    @ResponseBody
    private Map<String, Object> exceptionHandler(HttpServletRequest req, JsonSyntaxException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 401);
        map.put("msg", "数据结构错误");
        map.put("data", e.getMessage());
        return map;
    }

    @ExceptionHandler(value = CommunicationsException.class)
    @ResponseBody
    private Map<String, Object> exceptionHandler(HttpServletRequest req, CommunicationsException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 402);
        map.put("msg", "数据连接异常");
        map.put("data", e.getMessage());

        return map;
    }

    @ExceptionHandler(value = SQLException.class)
    @ResponseBody
    private Map<String, Object> sqlExceptionHandler(HttpServletRequest req, SQLException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 403);
        map.put("message", "数据库操作异常");
        map.put("data", e.getMessage());
        return map;
    }
    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseBody
    private Map<String, Object> exceptionHandler(HttpServletRequest req, IllegalArgumentException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 404);
        map.put("message", "非法参数");
        map.put("data", e.getMessage());
        return map;
    }
    @ExceptionHandler(value = MySQLIntegrityConstraintViolationException.class)
    @ResponseBody
    private Map<String, Object> MySQLIntegrityConstraintViolationException(HttpServletRequest req, Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 500);
        map.put("msg", "数据库插入错误");
        map.put("data", e.getMessage());
        return map;
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    private Map<String, Object> RuntimeException(HttpServletRequest req, Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 500);
        map.put("msg", e.getMessage());
        map.put("data", e.getMessage());
        return map;
    }
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private Map<String, Object> exceptionHandler(HttpServletRequest req, Exception e) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 500);
        map.put("msg", "程序异常");
        map.put("data", e.getMessage());
        return map;
    }
}
