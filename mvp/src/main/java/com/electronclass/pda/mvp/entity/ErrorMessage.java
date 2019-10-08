package com.electronclass.pda.mvp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 错误信息
 * Created by linlingrong on 2016-01-25.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorMessage implements Serializable {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
