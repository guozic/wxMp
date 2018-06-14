//package com.guozi.wx.controler;
//
//import com.guozi.wx.util.GetToken;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONObject;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Formatter;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * @author guozi
// * @date 2018-06-14 17:21
// */
//@Controller
//@RequestMapping(value = "jsConfig")
//public class JSConfigController {
//
//    @RequestMapping(value = "jsMethod.do", produces = "text/html;charset=UTF-8")
//    public void getJsMethod(HttpServletRequest req, HttpServletResponse resp){
//        try {
//            String url = req.getParameter("targetUrl");
//            System.out.println(url);
//            //获取全局access_token
//            GetToken.getAccess_token();
//            //获取JSAPI_TICKET 接口调用凭据
//            Constant.getJsApiTicket(Constant.ACCESS_TOKEN);
//            //进行签名算法
//            Map<String, String> map = sign(Constant.JSAPI_TICKET, url);
//            JSONObject jsonObject = new JSONObject();
//            for (String key : map.keySet()) {
//                jsonObject.put(key, map.get(key));
//            }
//            String scene_id = "2";
//            String postUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+Constant.ACCESS_TOKEN;
//            HttpPost httpPost = new HttpPost(postUrl);
//            HttpClient httpClient = new DefaultHttpClient();
//            String param = "{\"expire_seconds\": 2592000, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": "+scene_id+"}}}";
//            StringEntity entity = new StringEntity(param, "utf-8");
//            httpPost.setEntity(entity);
//            HttpResponse response = httpClient.execute(httpPost);
//            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//                String str = EntityUtils.toString(response.getEntity(),"utf-8");
//                JSONObject jsonObject2 = new JSONObject(str);
//                String ticket = (String) jsonObject2.get("ticket");
//                jsonObject.put("ticket", ticket);
//            }
//            jsonObject.put("appid", Constant.APPID);
//            resp.getWriter().write(jsonObject.toString());
//            resp.getWriter().close();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 签名算法
//     * @param jsapi_ticket
//     * @param url
//     * @return
//     */
//    public static Map<String, String> sign(String jsapi_ticket, String url) {
//        Map<String, String> ret = new HashMap<String, String>();
//        String nonce_str = create_nonce_str();
//        String timestamp = create_timestamp();
//        String string1;
//        String signature = "";
//
//        string1 = "jsapi_ticket=" + jsapi_ticket +
//                "&noncestr=" + nonce_str +
//                "&timestamp<span style='font-family:Arial, Helvetica, sans-serif;'> </span>=" + timestamp +
//                "&url=" + url;
//
//        try
//        {
//            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
//            crypt.reset();
//            crypt.update(string1.getBytes("UTF-8"));
//            signature = byteToHex(crypt.digest());
//        }
//        catch (NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            e.printStackTrace();
//        }
//
//        ret.put("url", url);
//        ret.put("jsapi_ticket", jsapi_ticket);
//        ret.put("nonceStr", nonce_str);
//        ret.put("timestamp", timestamp);
//        ret.put("signature", signature);
//
//        return ret;
//    }
//
//    private static String byteToHex(final byte[] hash) {
//        Formatter formatter = new Formatter();
//        for (byte b : hash)
//        {
//            formatter.format("%02x", b);
//        }
//        String result = formatter.toString();
//        formatter.close();
//        return result;
//    }
//
//    private static String create_nonce_str() {
//        return UUID.randomUUID().toString();
//    }
//
//    private static String create_timestamp() {
//        return Long.toString(System.currentTimeMillis() / 1000);
//    }
//}
