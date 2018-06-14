package com.guozi.wx.controler;

import com.guozi.wx.bean.TextMessage;
import com.guozi.wx.util.MessageUtil;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * @author guozi
 * @date 2018-06-14 17:46
 */
@Controller
public class MessageController {

    public void textMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String str = null;
//将request请求，传到Message工具类的转换方法中，返回接收到的Map对象
            Map<String, String> map = MessageUtil.xmlToMap(request);
//从集合中，获取XML各个节点的内容
            String ToUserName = map.get("ToUserName");
            String FromUserName = map.get("FromUserName");
            String CreateTime = map.get("CreateTime");
            String MsgType = map.get("MsgType");
            String Content = map.get("Content");
            String MsgId = map.get("MsgId");
            if(MsgType.equals("text")){//判断消息类型是否是文本消息(text)
                TextMessage message = new TextMessage();
                message.setFromUserName(ToUserName);//原来【接收消息用户】变为回复时【发送消息用户】
                message.setToUserName(FromUserName);
                message.setMsgType("text");
                message.setCreateTime(new Date().getTime());//创建当前时间为消息时间
                message.setContent("您好，"+FromUserName+"\n我是："+ToUserName
                        +"\n您发送的消息类型为："+MsgType+"\n您发送的时间为"+CreateTime
                        +"\n我回复的时间为："+message.getCreateTime()+"您发送的内容是"+Content);
                str = MessageUtil.objectToXml(message); //调用Message工具类，将对象转为XML字符串
            }

        out.print(str); //返回转换后的XML字符串
        out.close();
    }
}
