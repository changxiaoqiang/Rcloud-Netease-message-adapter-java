package cn.rongcloud.im.adapter.ext.neteaseSDK.messages;

import com.google.gson.Gson;

public abstract class Message {
    private final static Gson gson = new Gson();
    protected int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
