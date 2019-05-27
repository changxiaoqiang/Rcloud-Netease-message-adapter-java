package cn.rongcloud.im.adapter.ext.neteaseSDK.messages;

import cn.rongcloud.im.adapter.ext.neteaseSDK.util.MessageType;

public class TextMessage extends Message {
    private String msg;
    /**
     * 默认发送点对点消息
     * @param messageType
     * @param msg
     */
    public TextMessage(String msg) {
        this.msg = msg;
        this.type = MessageType.TEXT.getCode();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
