package cn.rongcloud.im.adapter.libs.neteaseSDK.messages;

import cn.rongcloud.im.adapter.libs.neteaseSDK.util.ConversationType;
import cn.rongcloud.im.adapter.libs.neteaseSDK.util.MessageType;

public class PicMessage extends Message {

    private String name;
    private String md5;
    private String url;
    private String ext;
    private int w;
    private int h;
    private int size;

    /**
     * 默认发送点对点消息
     * @param from
     * @param to
     * @param messageType
     * @param msg
     */
    public PicMessage(String from, String to, String msg,String name, String md5, String url, String ext, int w, int h, int size) {
        this.from = from;
        this.to = to;
        this.ope = ConversationType.PRIVATE.getCode();
        this.type = MessageType.PIC.getCode();
        this.name = name;
        this.md5 = md5;
        this.url = url;
        this.ext = ext;
        this.w = w;
        this.h = h;
        this.size = size;
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
    public PicMessage(String from, String to, ConversationType conversationType, String msg,String name, String md5, String url, String ext, int w, int h, int size) {
        this.from = from;
        this.to = to;
        this.ope = conversationType.getCode();
        this.type = MessageType.PIC.getCode();
        this.name = name;
        this.md5 = md5;
        this.url = url;
        this.ext = ext;
        this.w = w;
        this.h = h;
        this.size = size;
        body.put("msg", msg);
    }
}
