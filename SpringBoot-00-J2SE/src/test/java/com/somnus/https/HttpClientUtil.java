package com.somnus.https;

import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.somnus.exception.HttpStatusException;
import com.somnus.https.HttpClientManager;

/**
 * @author Somnus
 * @version V1.0
 * @ClassName: HttpClientUtils.java
 * @Description: TODO
 * @Date 2016年10月13日 下午3:40:17
 */
public class HttpClientUtil {

    private transient static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);

    private static RequestConfig requestConfig;

    private static final int MAX_TIMEOUT = 10000;

    static {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
    }

    /**
     * Content-type : application/json
     *
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public static String doJsonPost(String url, String json) throws Exception {
        return doJsonPost(url, json, null, null);
    }

    /**
     * Content-type : text/html
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String doGet(String url) throws Exception {
        return doGet(url, null, null);
    }

    /**
     * Content-type : text/html
     *
     * @param url
     * @param ks
     * @return
     * @throws Exception
     */
    public static String doGet(String url, String path, String password) throws Exception {
        return doGet(url, null, path, password);
    }

    /**
     * Content-type : text/html
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String doGet(String url, Map<String, String> param) throws Exception {
        return doGet(url, param, null, null);
    }

    /**
     * Content-type : multipart/form-data
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String doUploadPost(String url, File file) throws Exception {
        return doUploadPost(url, file, null, null);
    }

    /**
     * Content-type : application/x-www-form-urlencoded
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String, String> param) throws Exception {
        return doPost(url, param, null, null);
    }

    /**
     * Content-type : application/xml
     *
     * @param url
     * @param xml
     * @return
     * @throws Exception
     */
    public static String doXmlPost(String url, String xml) throws Exception {
        return doXmlPost(url, xml, null, null);
    }

    /**
     * Content-type : application/json
     *
     * @param url
     * @param json
     * @param ks
     * @return
     * @throws Exception
     */
    public static String doJsonPost(String url, String json, String path, String password) throws Exception {
        Validate.notNull(url, "url must be required.");
        Validate.notNull(json, "json must be required.");
        //创建HttpClient对象
        CloseableHttpClient httpclient = HttpClientManager.getSSLHttpClient(path, password);
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
            // 创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

            // 开始执行http请求
            StopWatch sw = new StopWatch();
            sw.start();
            httpResponse = httpclient.execute(httpPost);
            sw.stop();

            // 获得响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            log.info("statusCode:" + statusCode);
            log.info("调用API 花费时间(单位：毫秒)：" + sw.getTime());

            // 取出应答字符串
            HttpEntity httpEntity = httpResponse.getEntity();
            resultString = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));

            // 判断返回状态是否为200
            if (statusCode != HttpStatus.SC_OK) {
                throw new HttpStatusException(String.format("\n\tStatus:%s\n\tError Message:%s", statusCode, resultString));
            }
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            httpclient.close();
        }
        return resultString;
    }

    /**
     * Content-type : text/html
     *
     * @param url
     * @param param
     * @param ks
     * @return
     * @throws Exception
     */
    public static String doGet(String url, Map<String, String> param, String path, String password) throws Exception {
        Validate.notNull(url, "url must be required.");
        //创建HttpClient对象
        CloseableHttpClient httpclient = HttpClientManager.getSSLHttpClient(path, password);
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
            //创建uri
            URIBuilder builder = new URIBuilder(url);
            if (MapUtils.isNotEmpty(param)) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建httpGet请求
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(requestConfig);

            // 开始执行http请求
            StopWatch sw = new StopWatch();
            sw.start();
            httpResponse = httpclient.execute(httpGet);
            sw.stop();

            // 获得响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            log.info("statusCode:" + statusCode);
            log.info("调用API花费时间(单位：毫秒)：" + sw.getTime());

            // 取出应答字符串
            HttpEntity httpEntity = httpResponse.getEntity();
            resultString = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
            // 去掉返回结果中的"\r"字符，否则会在结果字符串后面显示一个小方格
            resultString.replaceAll("\r", "");

            // 判断返回状态是否为200
            if (statusCode != HttpStatus.SC_OK) {
                throw new HttpStatusException(String.format("\n\tStatus:%s\n\tError Message:%s", statusCode, resultString));
            }
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            httpclient.close();
        }
        return resultString;
    }

    /**
     * Content-type : multipart/form-data
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String doUploadPost(String url, File file, String path, String password) throws Exception {
        Validate.notNull(url, "url must be required.");
        //创建HttpClient对象
        CloseableHttpClient httpclient = HttpClientManager.getSSLHttpClient(path, password);
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
            // 创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(MultipartEntityBuilder.create().addBinaryBody("file", file).build());

            // 开始执行http请求
            StopWatch sw = new StopWatch();
            sw.start();
            httpResponse = httpclient.execute(httpPost);
            sw.stop();

            // 获得响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            log.info("statusCode:" + statusCode);
            log.info("调用API花费时间(单位：毫秒)：" + sw.getTime());

            // 取出应答字符串
            HttpEntity httpEntity = httpResponse.getEntity();
            resultString = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));

            // 判断返回状态是否为200
            if (statusCode != HttpStatus.SC_OK) {
                throw new HttpStatusException(String.format("\n\tStatus:%s\n\tError Message:%s", statusCode, resultString));
            }
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            httpclient.close();
        }
        return resultString;
    }

    /**
     * Content-type : application/x-www-form-urlencoded
     *
     * @param url
     * @param param
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String, String> param, String path, String password) throws Exception {
        Validate.notNull(url, "url must be required.");
        //创建HttpClient对象
        CloseableHttpClient httpclient = HttpClientManager.getSSLHttpClient(path, password);
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
            // 创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            if (MapUtils.isNotEmpty(param)) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (String key : param.keySet()) {
                    params.add(new BasicNameValuePair(key, param.get(key)));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(params, Charset.forName("UTF-8")));
            }

            // 开始执行http请求
            StopWatch sw = new StopWatch();
            sw.start();
            httpResponse = httpclient.execute(httpPost);
            sw.stop();

            // 获得响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            log.info("statusCode:" + statusCode);
            log.info("调用API花费时间(单位：毫秒)：" + sw.getTime());

            // 取出应答字符串
            HttpEntity httpEntity = httpResponse.getEntity();
            resultString = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));

            // 判断返回状态是否为200
            if (statusCode != HttpStatus.SC_OK) {
                throw new HttpStatusException(String.format("\n\tStatus:%s\n\tError Message:%s", statusCode, resultString));
            }
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            httpclient.close();
        }
        return resultString;
    }

    /**
     * Content-type : application/xml
     *
     * @param url
     * @param json
     * @param ks
     * @return
     * @throws Exception
     */
    public static String doXmlPost(String url, String xml, String path, String password) throws Exception {
        Validate.notNull(url, "url must be required.");
        Validate.notNull(xml, "xml must be required.");
        //创建HttpClient对象
        CloseableHttpClient httpclient = HttpClientManager.getSSLHttpClient(path, password);
        String resultString = "";
        CloseableHttpResponse httpResponse = null;
        try {
            // 创建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new StringEntity(xml, ContentType.APPLICATION_XML));

            // 开始执行http请求
            StopWatch sw = new StopWatch();
            sw.start();
            httpResponse = httpclient.execute(httpPost);
            sw.stop();

            // 获得响应状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            log.info("statusCode:" + statusCode);
            log.info("调用API 花费时间(单位：毫秒)：" + sw.getTime());

            // 取出应答字符串
            HttpEntity httpEntity = httpResponse.getEntity();
            resultString = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));

            // 判断返回状态是否为200
            if (statusCode != HttpStatus.SC_OK) {
                throw new HttpStatusException(String.format("\n\tStatus:%s\n\tError Message:%s", statusCode, resultString));
            }
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
            httpclient.close();
        }
        return resultString;
    }

}
