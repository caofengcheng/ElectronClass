package com.electronclass.pda.mvp.base;

import android.net.ParseException;
import android.text.TextUtils;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.electronclass.pda.mvp.entity.ErrorMessage;
import com.electronclass.pda.mvp.rest.ExceptionMessageEnum;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

class HttpExceptionUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpExceptionUtil.class);

    static String exceptionHandler(Throwable e) {
        String errorMsg;
        if (e instanceof UnknownHostException) {
            errorMsg = "网络不可用";
        } else if (e instanceof SocketTimeoutException) {
            errorMsg = "请求网络超时";
        } else if (e instanceof ConnectException) {
            errorMsg = "连接失败";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorMsg = convertStatusCode(httpException);
        } else if (e instanceof ParseException || e instanceof JSONException) {
            errorMsg = "数据解析错误";
        } else if (e instanceof NullPointerException) {
            errorMsg = "获取数据失败";
        } else {
            errorMsg = TextUtils.isEmpty(e.getMessage()) ? e.toString() : e.getMessage();
        }
        logger.debug(errorMsg);
        return errorMsg;
    }

    private static String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() >= 500 && httpException.code() < 600) {
            msg = "服务器处理请求出错";
        } else if (httpException.code() >= 400 && httpException.code() < 500) {
            msg = "服务器无法处理请求";
            ObjectMapper obj = new ObjectMapper();
            try {
                ResponseBody responseBody = httpException.response().errorBody();
                if (responseBody != null) {
                    String       errStr       = responseBody.string();
                    ErrorMessage errorMessage = obj.readValue(errStr, ErrorMessage.class);
                    if (errorMessage != null) {
                        String temp = ExceptionMessageEnum.getValue(errorMessage.getMessage());
                        msg = StringUtils.defaultIfEmpty(temp, errorMessage.getMessage());
                    }
                }
            } catch (IOException e) {
                Log.getStackTraceString(e);
            }
        } else if (httpException.code() >= 300 && httpException.code() < 400) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }
}