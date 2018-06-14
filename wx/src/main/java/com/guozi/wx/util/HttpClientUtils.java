package com.guozi.wx.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author guozi
 * @date 2018-06-14 17:16
 */
public class HttpClientUtils {

    public JSONObject doGet(String url) {
        // 指定get请求
        HttpGet httpGet = new HttpGet(url);
        // 创建httpclient
        HttpClient httpClient = new DefaultHttpClient();
        // 发送请求
        HttpResponse httpResponse;
        //返回的json
        JSONObject jsonObject = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            // 验证请求是否成功
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 得到请求响应信息
                String str = EntityUtils.toString(httpResponse.getEntity(),
                        "utf-8");
                // 返回json
                jsonObject = new JSONObject(str);
            }
        } catch (ClientProtocolException e) {
            System.out.println("HttpClientUtils ------ clientHttP 失败-----");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("HttpClientUtils ------ 文件流失败 失败-----");
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("HttpClientUtils ------ json 失败-----");
            e.printStackTrace();
        }
        return jsonObject;
    }


    public JSONObject doPost(String url, JSONObject jsonData) {
        // 指定Post请求
        HttpPost httpPost = new HttpPost(url);
        // 创建httpclient
        HttpClient httpClient = new DefaultHttpClient();
        // 发送请求
        HttpResponse httpResponse;
        // 返回的json
        JSONObject jsonObject = null;
        // 封装post请求数据
        StringEntity entity = new StringEntity(jsonData.toString(), "utf-8");
        httpPost.setEntity(entity);
        try {
            // 发送请求
            httpResponse = httpClient.execute(httpPost);
            // 判断请求是否成功
            if(httpResponse.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                // 得到请求响应信息
                String str = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                // 返回json
                jsonObject = new JSONObject(str);
            }
        } catch (ClientProtocolException e) {
            System.out.println("HttpClientUtils ------ clientHttP 失败-----");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("HttpClientUtils ------ 文件流失败 失败-----");
            e.printStackTrace();
        } catch (JSONException e) {
            System.out.println("HttpClientUtils ------ json 失败-----");
            e.printStackTrace();
        }
        return jsonObject;
    }

}
