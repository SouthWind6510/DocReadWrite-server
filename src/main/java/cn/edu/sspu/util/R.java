package cn.edu.sspu.util;
/**
 * Json统一结果返回类
 */

public class R {
    private int code;
    private String msg;
    private Object data;

    public R(int code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static R ok(int code, String msg, Object data){
        return new R(code,msg,data);
    }

    public static R fail(int code, String msg, Object data){
        return new R(code,msg,data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
}
