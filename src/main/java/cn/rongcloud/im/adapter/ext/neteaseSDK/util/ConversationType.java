package cn.rongcloud.im.adapter.ext.neteaseSDK.util;

public enum ConversationType {
    PRIVATE(0, "单聊"), GROUP(1, "群聊");
    private int code;
    private String type;

    ConversationType(int code, String type) {
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
