package com.electronclass.pda.mvp.rest;



import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RestManager {
    private RestApi restApi;

    public static RestManager getInstance() {
        return InstanceHolder.mInstance;
    }

    public static RestApi getRestApi() {
        return getInstance().restApi;
    }

    public void initRest(String url) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new VoidInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    private List<Cookie> session;

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        session = cookies;
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return session == null ? new ArrayList<Cookie>() : session;
                    }
                })
                .build();

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
//                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create(mapper))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 使用RxJava作为回调适配器
                    .client(okHttpClient)
                    .build();
            restApi = retrofit.create(RestApi.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class InstanceHolder {
        private static final RestManager mInstance = new RestManager();
    }
}
