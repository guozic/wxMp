package com.guozi.wx.bean;

/**
 * @author guozi
 * @date 2018-06-14 17:43
 */
public class TextMessage {

    private String ToUserName;

    private String FromUserName;

    private Long CreateTime;

    private String MsgType;

    private String Content;

    private String MsgId;


    public String getToUserName() {

        return ToUserName;

    }

    public void setToUserName(String toUserName) {

        ToUserName = toUserName;

    }

    public String getFromUserName() {

        return FromUserName;

    }

    public void setFromUserName(String fromUserName) {

        FromUserName = fromUserName;

    }

    public Long getCreateTime() {

        return CreateTime;

    }

    public void setCreateTime(long l) {

        CreateTime = l;

    }

    public String getMsgType() {

        return MsgType;

    }

    public void setMsgType(String msgType) {

        MsgType = msgType;

    }

    public String getContent() {

        return Content;

    }

    public void setContent(String content) {

        Content = content;

    }

    public String getMsgId() {

        return MsgId;

    }

    public void setMsgId(String msgId) {

        MsgId = msgId;

    }

    public TextMessage(String toUserName, String fromUserName,

                       Long createTime, String msgType, String content, String msgId) {

        super();

        ToUserName = toUserName;

        FromUserName = fromUserName;

        CreateTime = createTime;

        MsgType = msgType;

        Content = content;

        MsgId = msgId;

    }

    public TextMessage() {

        super();

    }

}
