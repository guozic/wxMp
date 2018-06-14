package com.guozi.wx.controler;

import com.guozi.wx.util.MessageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 验证服务器 获取测试账号 成为开发者
 *
 * @author guozi
 * @date 2018-06-14 17:01
 */
@Controller
@RequestMapping(value = "api")
public class WeiXinController {

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    @GetMapping(value = "guozic9887")
    @ResponseBody
    public void getWeiXinMethod(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean validate = validate(request);
        if (validate) {
            response.getWriter().write(request.getParameter("echostr"));
            response.getWriter().close();
        }

        textMessage(request, response);

    }

    private boolean validate(HttpServletRequest req) throws IOException {
        String signature = req.getParameter("signature");//微信加密签名
        String timestamp = req.getParameter("timestamp");//时间戳
        String nonce = req.getParameter("nonce");//随机数
        List<String> list = new ArrayList<String>();
        list.add("guozicheng");
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);//字典排序
        String s = "";
        for (int i = 0; i < list.size(); i++) {
            s += (String) list.get(i);
        }
        if (encode("SHA1", s).equalsIgnoreCase(signature)) {
            return true;
        } else {
            return false;
        }
    }

    public static String encode(String algorithm, String str) {
        if (str == null) {
            return null;
        }
        try {
            //Java自带的加密类
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            //转为byte
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }


    public void textMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String str = null;
//将request请求，传到Message工具类的转换方法中，返回接收到的Map对象
        Map<String, String> map = MessageUtil.xmlToMap(request);
//从集合中，获取XML各个节点的内容
        if (null != map) {
            String ToUserName = map.get("ToUserName");
            String FromUserName = map.get("FromUserName");
            String CreateTime = map.get("CreateTime");
            String MsgType = map.get("MsgType");
            String Content = map.get("Content");
            String MsgId = map.get("MsgId");

            String message = null;
            if (MsgType.equals(MessageUtil.MESSAGE_TEXT)) {//判断是否为文本消息类型
                if (Content.equals("1")) {
                    message = MessageUtil.initText(ToUserName, FromUserName,
                            "对啊！我也是这么觉得！姜浩帅哭了！");
                } else if (Content.equals("2")) {
                    message = MessageUtil.initText(ToUserName, FromUserName,
                            "好可怜啊！你年级轻轻地就瞎了！");
                } else if (Content.equals("?") || Content.equals("？")) {
                    message = MessageUtil.initText(ToUserName, FromUserName,
                            MessageUtil.menuText());
                } else {
                    message = MessageUtil.initText(ToUserName, FromUserName,
                            "没让你选的就别瞎嘚瑟！！！");
                }

            } else if (MsgType.equals(MessageUtil.MESSAGE_EVENT)) {//判断是否为事件类型
//从集合中，或许是哪一种事件传入
                String eventType = map.get("Event");
//关注事件
                if (eventType.equals(MessageUtil.MESSAGE_SUBSCRIBE)) {
                    message = MessageUtil.initText(ToUserName, FromUserName,
                            MessageUtil.menuText());
                }
            }
            System.out.println(message);
            out.print(message);
        }
    }
}
