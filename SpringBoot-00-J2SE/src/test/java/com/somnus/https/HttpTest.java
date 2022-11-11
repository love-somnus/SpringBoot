package com.somnus.https;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Somnus
 * @version V1.0
 * @Description: https://api-vip1.huadata.com/result
 * @date 2015年12月16日 下午2:37:26
 */
@Slf4j
public class HttpTest {

    @Test
    public void doJsonPost(){
        String url = "https://httpbin.org/post";
        JSONObject param = new JSONObject();
        param.put("username", "admin");
        param.put("password", "123456");
        System.out.println("body:" + HttpClientUtil.doJsonPost(url, param.toJSONString()));
    }

    @Test
    @SneakyThrows
    public void doJsonPostByFluent() {
        String url = "https://httpbin.org/post";
        JSONObject param = new JSONObject();
        param.put("username", "admin");
        param.put("password", "123456");
        String response = Request.Post(url)
                .connectTimeout(1000)
                .socketTimeout(1000)
                .bodyString(param.toJSONString(), ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();
        System.out.println(response);
    }

    @Test
    public void doPathParamGet() {
        String url = "https://httpbin.org/html";
        System.out.println("body:" + HttpClientUtil.doGet(url));
    }

    @Test
    @SneakyThrows
    public void doPathParamOptionByFluent() {
        URL url = new URL("http://m801.music.126.net/20220906175024/0d1d898c463838a8f25ce84e87f6c081/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/14122129619/8420/27c0/42c4/ebdd98f7b5236349aecf4293fa57f2cb.mp3");
        URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
        HttpResponse response= Request.Get(new URIBuilder(uri).build()).execute().returnResponse();
        StatusLine statusLine = response.getStatusLine();
        int code = statusLine.getStatusCode();
        System.out.println(code);
    }

    @Test
    @SneakyThrows
    public void doPathParamGetByFluent() {
        String response = Request.Get("http://pv.sohu.com/cityjson?ie=utf-8").execute().returnContent().asString();
        System.out.println(response);
        String json = StringUtils.substringBetween(response, "=", ";").trim();
        System.out.println(json);
        JSONObject obj = JSON.parseObject(json);
        System.out.println(obj.getString("cip"));
    }

    @Test
    public void doQueryParamGet() {
        String url = "https://httpbin.org/cookies/set";
        Map<String, String> param = new HashMap<>();
        param.put("username", "admin");
        param.put("password", "123456");
        System.out.println("body:" + HttpClientUtil.doGet(url, param));
    }

    @Test
    @SneakyThrows
    public void doQueryParamGetByFluent() {
        String url = "https://httpbin.org/cookies/set";
        String response = Request.Get(new URIBuilder(url)
                .addParameter("username", "admin")
                .addParameter("password", "123456")
                .build()
        )
                .execute().returnContent().asString();
        System.out.println(response);
    }

    @Test
    @SneakyThrows
    public void doQueryParamGetByFluent2() {
        String url = "https://httpbin.org/cookies/set";
        Map<String,String> params = ImmutableMap.of("username", "admin", "password", "123456");
        List<NameValuePair> pairs = params.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        String response = Request.Get(new URIBuilder(url).addParameters(pairs).build())
                .addHeader("","")
                .execute().returnContent().asString();
        System.out.println(response);
    }

    @Test
    @SneakyThrows
    public void doQueryParamGetByFluent3() {
        String url = "https://login.netease.com/connect/authorize";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>(
                ImmutableMap.of("response_type", ImmutableList.of("code"),
                        "client_id", ImmutableList.of("0ff62954802f11e98e6d246e965dfd84"),
                        "redirect_uri", ImmutableList.of("https://cms.nesh.netease.com"),
                        "scope",ImmutableList.of("openid%20fullname")));
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(url).queryParams(params).build();
        String response = Request.Get(uriComponents.toString()).execute().returnContent().asString();
        System.out.println(response);
    }

    @Test
    @SneakyThrows
    public void doFormParamPost() {
        String url = "https://httpbin.org/post";
        Map<String, String> param = new HashMap<>();
        param.put("custname", "admin");
        param.put("custtel", "123456");
        System.out.println("body:" + HttpClientUtil.doPost(url, param));
    }

    @Test
    @SneakyThrows
    public void doFormParamPostByFluent() {
        String url = "https://api.baochuangames.com/uac/vote/wybc/survey";
        String response = Request.Post(url).bodyForm(Form.form()
                .add("gameAbbr", "wybc")
                        .add("title", "妖帝简中字体测试")
                        .add("ext2", "8岁以下")
                        .add("ext1", "男")
                        .add("favorite", "0")
                .build()
        )
                .execute().returnContent().asString();
        System.out.println(response);
    }

    @Test
    @SneakyThrows
    public void doFormParamPostByFluent2() {
        String url = "https://httpbin.org/post";
        Map<String,String> params = ImmutableMap.of("system", "wt-cmc", "requestUrl", "game/all");
        List<NameValuePair> pairs = params.entrySet().stream().map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        String response = Request.Post(url).bodyForm(pairs).addHeader("Authorization","xxxxx").execute().returnContent().asString();
        System.out.println(response);
    }

    @Test
    public void doUpload()  {
        String url = "http://localhost:8080/SpringMVC/databind/doUpload";
        String path = Thread.currentThread().getContextClassLoader().getResource("excel/80034.xls").getPath();
        System.out.println("body:" + HttpClientUtil.doUploadPost(url, new File(path)));
    }

    @Test
    @SneakyThrows
    public void doUploadByFluent() {
        String url = "http://192.168.97.101:30000/upload/cmty/image";
        String path = Thread.currentThread().getContextClassLoader().getResource("luoli.jpg").getPath();
        String response = Request.Post(url).addHeader("Authorization","Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJzMzk5NiIsImV4cCI6MTU2NTMzNTkwMH0.a5fr1buM7ptovnkEoFiFazBp3mCDiUZWq3XTWgchvMClMzO9bnb2zuamCfbuoxmViAPTzb1YFcJou88KOJpnBzvXsKvEWgE58V3HaAouYMKBlZKB6BSJ84LTlIDIOBHjIn2z9SeajSWOWf65VRiMChdJcVvyLWHFHrZkEV4M6FY")
                /*.addHeader("Content-Type","multipart/form-data")*/
                .body(MultipartEntityBuilder.create().addBinaryBody("file", new File(path)).build())
                .execute().returnContent().asString();
        System.out.println(response);
    }

    @Test
    @SneakyThrows
    public void doDownload()  {
        String url = "https://httpbin.org/image/jpeg";
        byte[] buff = Request.Get(new URIBuilder(url).build()).execute().returnContent().asBytes();
        OutputStream os = new FileOutputStream(new File("target/classes/e01.jpg"));
        try {
            IOUtils.write(buff, os);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    @Test
    @SneakyThrows
    public void uriBuilder()  {

        URL url = new URL("https://httpbin.org/image/jpeg?keyword=1");

        URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);

        byte[] buff = Request.Get(new URIBuilder(uri).build()).execute().returnContent().asBytes();

        log.info("Protocol:{} >> Host:{} >> Path:{} >> Query:{} ",url.getProtocol(), url.getHost(), url.getPath(),url.getQuery());
    }

    @SneakyThrows
    public static void main(String[] args) {
        String key = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzZDQ3ODY0MC0wYjBkLTAxMzctNmQ5MS0wYTU4NjQ2MTRkN2QiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTQ5MzI3NTIwLCJwdWIiOiJzZW1jIiwidGl0bGUiOiJ2YWluZ2xvcnkiLCJhcHAiOiJqZWZmcmV5LXpoYW5nMy1jb3JwLW5ldGVhc2UtY29tLXMtYXBwIiwic2NvcGUiOiJjb21tdW5pdHkiLCJsaW1pdCI6MTB9.rle1LKISqypy2uBFQJHWDMGTwxOIa7bwYCL0SigJjqU";
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("https://api.dc01.gamelockerapp.com/shards/na/players/10000").queryParams(null).build();
        URL url = new URL(uriComponents.toString());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Authorization", key);
        urlConnection.setRequestProperty("Accept", "application/vnd.api+json");

        log.info("参数为:{}", uriComponents.toString());
        String resp = null;
        InputStream inputStream = urlConnection.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream,"UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer stringBuffer = new StringBuffer();
        while ((resp = bufferedReader.readLine()) != null){
            stringBuffer.append(resp);
        }
        bufferedReader.close();
        reader.close();
        inputStream.close();
        log.info("响应结果为:{}", stringBuffer.toString());
    }
}
