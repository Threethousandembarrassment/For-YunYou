package com.yunyou.util;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by HL on 2017/10/9.
 */

public class HttpUtils {
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public String login(String url, String json) throws IOException {
        //把请求的内容字符串转换为json
        RequestBody body = RequestBody.create(JSON, json);
        //RequestBody formBody = new FormEncodingBuilder()
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        String result = response.body().string();

        return result;

    }

    public String bolwingJson(String username,String zone, String password) {
        return "{'user_phone':" + username + ","+"'zone':"+zone+ ","+"'pwd':" + password + "}";
        //   "{'username':" + username + ","+"'password':"+password+"}";
        }
}
