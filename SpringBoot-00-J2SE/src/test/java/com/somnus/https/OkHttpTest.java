package com.somnus.https;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.junit.Test;

import java.io.*;

/**
 * @author Somnus
 * @version V1.0
 * @Description: https://api-vip1.huadata.com/result
 * @date 2015年12月16日 下午2:37:26
 */
@Slf4j
public class OkHttpTest {

    /* 异步GET请求 */
    @Test
    public void asyncGet(){
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url("https://httpbin.org/html").get().build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                log.error("onFailure: {}" , e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                log.info("onResponse: " + response.body().string());
            }
        });

        System.out.println("$$$$$$$$$$$$$$$$$$");
        /** 主线程执行完毕后，如果子线程还没执行完成，就不会管的，测试的时候谨慎使用，main则不会*/
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /* 同步GET请求 */
    @Test
    @SneakyThrows
    public void syncGet(){
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url("https://httpbin.org/html").build();

        Response response = okHttpClient.newCall(request).execute();

        log.info("run: " + response.body().string());
    }

    /* 异步POST方式提交普通字符串 */
    @Test
    public void asyncPostString(){
        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");

        Request request = new Request.Builder().addHeader("Authorization", "123456").url("https://httpbin.org/post").post(RequestBody.create(mediaType, "love.nacos")).build();

        OkHttpClient okHttpClient = new OkHttpClient();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                log.error("onFailure: {}" , e.getMessage());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                log.info(response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    log.info(headers.name(i) + ":" + headers.value(i));
                }
                log.info("onResponse: " + response.body().string());
            }
        });

        /** 主线程执行完毕后，如果子线程还没执行完成，就不会管的，测试的时候谨慎使用，main则不会*/
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("$$$$$$$$$$$$$$$$$$");
    }

    /* 异步POST方式提交json */
    @Test
    public void asyncPostJson(){
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

        Request request = new Request.Builder().addHeader("Authorization", "123456").url("https://httpbin.org/post").post(RequestBody.create(mediaType, "{\"user\":{\"username\":\"owen\",\"password\":\"passw0rd\", \"age\":24}}")).build();

        OkHttpClient okHttpClient = new OkHttpClient();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull  Call call, @NonNull  IOException e) {
                log.error("onFailure: {}" , e.getMessage());
            }
            @Override
            public void onResponse(@NonNull  Call call, @NonNull  Response response) throws IOException {
                log.info(response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    log.info(headers.name(i) + ":" + headers.value(i));
                }
                log.info("onResponse: " + response.body().string());
            }
        });

        /** 主线程执行完毕后，如果子线程还没执行完成，就不会管的，测试的时候谨慎使用，main则不会*/
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("$$$$$$$$$$$$$$$$$$");
    }

    /* 异步POST方式提交表单键值对 */
    @Test
    public void asyncPostForm(){
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder().add("username", "admin").add("password", "123456").build();

        Request request = new Request.Builder().url("https://httpbin.org/post").post(requestBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                log.error("onFailure: {}" , e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                log.info(response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    log.info(headers.name(i) + ":" + headers.value(i));
                }
                log.info("onResponse: " + response.body().string());
            }
        });

        /** 主线程执行完毕后，如果子线程还没执行完成，就不会管的，测试的时候谨慎使用，main则不会*/
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("$$$$$$$$$$$$$$$$$$");
    }

    /* 异步POST方式提交文件 */
    @Test
    public void asyncPostFile(){
        OkHttpClient okHttpClient = new OkHttpClient();

        MediaType mediaType = MediaType.parse("multipart/form-data; charset=utf-8");

        File file = new File("C:/Windows/Media/tada.wav");

        RequestBody fileBody = RequestBody.create(mediaType, file);

        // 不仅可以支持传文件，还可以在传文件的同时，传参数
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", "123456")
                .addFormDataPart("接收文件的参数名", "文件名", fileBody)
                .build();

        Request request = new Request.Builder().url("https://httpbin.org/post").post(requestBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull  Call call, @NonNull IOException e) {
                log.error("onFailure: {}" , e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                log.info(response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    log.info(headers.name(i) + ":" + headers.value(i));
                }
                log.info("onResponse: " + response.body().string());
            }
        });

        /** 主线程执行完毕后，如果子线程还没执行完成，就不会管的，测试的时候谨慎使用，main则不会*/
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("$$$$$$$$$$$$$$$$$$");
    }

}
