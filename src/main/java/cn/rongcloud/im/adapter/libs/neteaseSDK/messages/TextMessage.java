package cn.rongcloud.im.adapter.libs.neteaseSDK.messages;

import cn.rongcloud.im.adapter.libs.neteaseSDK.util.ConversationType;
import cn.rongcloud.im.adapter.libs.neteaseSDK.util.MessageType;

public class TextMessage extends Message {
    /**
     * 默认发送点对点消息
     * @param from
     * @param to
     * @param messageType
     * @param msg
     */
    public TextMessage(String from, String to, String msg) {
        this.from = from;
        this.to = to;
        this.ope = ConversationType.PRIVATE.getCode();
        this.type = MessageType.TEXT.getCode();
        body.put("msg", msg);
    }

    /**
     * 自定义发送会话类型，群聊 ope = 1
     * @param from
     * @param to
     * @param ope
     * @param messageType
     * @param msg
     */
    public TextMessage(String from, String to, ConversationType conversationType, String msg) {
        this.from = from;
        this.to = to;
        this.ope = conversationType.getCode();
        this.type = MessageType.TEXT.getCode();
        body.put("msg", msg);
    }


}
