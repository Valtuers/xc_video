package net.xcvideo.utils;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装http
 */
public class HttpUtils {

    public static final Gson gson = new Gson();

    public static Map<String,Object> doGet(String url){
        Map<String,Object> map = new HashMap<>();
        CloseableHttpClient httpClients = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)                    //连接超时
                .setConnectionRequestTimeout(5000)          //请求超时
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)                  //允许重定向
                .build();


        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        try {
            HttpResponse httpResponse = httpClients.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                String entity = EntityUtils.toString(httpResponse.getEntity());
                map = gson.fromJson(entity,map.getClass());
            }
        } catch (IOException e) {
            e.getMessage();
        }finally {
            try {
                httpClients.close();
            } catch (IOException e) {
                e.getMessage();
            }
        }
        return map;
    }

    public static String doPost(String url,String data,int timeout){
        CloseableHttpClient httpClients = HttpClients.createDefault();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(timeout)                     //连接超时
                .setConnectionRequestTimeout(timeout)           //请求超时
                .setSocketTimeout(timeout)
                .setRedirectsEnabled(true)                      //允许重定向
                .build();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type","text/html;charset=UTF-8");
        httpPost.setConfig(requestConfig);

        if(data != null && data instanceof String){
            StringEntity stringEntity = new StringEntity(data,"UTF-8");
            httpPost.setEntity(stringEntity);
        }

        try {
            HttpResponse httpResponse = httpClients.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IOException e) {
            e.getMessage();
        }finally {
            try {
                httpClients.close();
            } catch (IOException e) {
                e.getMessage();
            }
        }
        return null;
    }
}
