package com.guozi.wx.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 验证服务器 获取测试账号 成为开发者
 * 方式二
 * @author guozi
 * @date 2018-06-14 15:55
 * @date 2018-06-14 15:55
 */
//@WebFilter(filterName="myFilter",urlPatterns="api/guozic9887")
public class WxFilter implements Filter {

    //这个Token是给微信开发者接入时填的
    //可以是任意英文字母或数字，长度为3-32字符
    private static String Token = "guozicheng";

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("WeixinUrlFilter启动成功!");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        //微信服务器将发送GET请求到填写的URL上,这里需要判定是否为GET请求
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        System.out.println("获得微信请求:" + request.getMethod() + " 方式");
        if (isGet) {
            //验证URL真实性
            String signature = request.getParameter("signature");// 微信加密签名
            String timestamp = request.getParameter("timestamp");// 时间戳
            String nonce = request.getParameter("nonce");// 随机数
            String echostr = request.getParameter("echostr");//随机字符串
            List<String> params = new ArrayList<String>();
            params.add(Token);
            params.add(timestamp);
            params.add(nonce);
            //1. 将token、timestamp、nonce三个参数进行字典序排序
            Collections.sort(params, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            //2. 将三个参数字符串拼接成一个字符串进行sha1加密
            String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));
            if (temp.equals(signature)) {
                response.getWriter().write(echostr);
            }
        } else {
            //处理接收消息
            response.getWriter().write("nihao hello!!!!");
        }
    }

    @Override
    public void destroy() {

    }
}
