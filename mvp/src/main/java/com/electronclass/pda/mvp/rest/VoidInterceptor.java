package com.electronclass.pda.mvp.rest;



import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by linlingrong on 2016-08-02.
 */
public class VoidInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private Logger logger = LoggerFactory.getLogger(VoidInterceptor.class);

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        // try the request
        logger.debug("发送请求: {}", request.url().toString());
        Response response = chain.proceed(request);
        /* 通过如下的办法曲线取到请求完成的数据
         *
         * 原本想通过  originalResponse.body().string()
         * 去取到请求完成的数据,但是一直报错,不知道是okhttp的bug还是操作不当
         *
         * 然后去看了okhttp的源码,找到了这个曲线方法,取到请求完成的数据后
         */
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            BufferedSource source = responseBody.source();
            if (source != null) {
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                if (charset != null) {
                    String bodyString = buffer.clone().readString(charset);
                    logger.debug("{} 返回: {}", request.url().toString(), bodyString);
                    logger.debug("body string length: {}", bodyString.length());
                    if (bodyString.isEmpty() && response.body() != null) {
                        response.body().source().buffer().writeString("null", charset);
                    }
                }
            }
        }
        return response;
    }
}
