package com.electronclass.pda.mvp.entity;

/**
 * 页面：
 */
public class ServiceResponse<T> {

    /**
     * "msg": "操作成功",
     *
     * "code": "200",
     *
     * "data":
     */

    private String msg;
    private String  code;
    private T       data;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
