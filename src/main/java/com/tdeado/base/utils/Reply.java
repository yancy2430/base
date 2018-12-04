package com.tdeado.base.utils;

public class Reply {
    private boolean success;
    private String msg;
    private Object data;

    private Reply(boolean code, String msg, Object data) {
        this.success = code;
        this.msg = msg;
        this.data = data;
    }
    public static Reply ok(Object object){
        return new Reply(true,"请求成功",object);
    }

    public static Reply error(String msg){
        return new Reply(false,msg,null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
