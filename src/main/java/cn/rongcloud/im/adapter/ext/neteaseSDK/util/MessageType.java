package cn.rongcloud.im.adapter.ext.neteaseSDK.util;

public enum MessageType {
    TEXT(0, "文本消息"),  PIC(1, "图片消息"),  VOICE(2, "语音消息"), VIDEO(3, "视频消息"),  LBS(4, "地理位置消息"), FILE(6, "文件消息");

    int code;
    String type;

    private MessageType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return type;
    }
}
