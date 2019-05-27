package cn.rongcloud.im.adapter.ext.neteaseSDK.models;

import cn.rongcloud.im.adapter.ext.neteaseSDK.messages.Message;
import cn.rongcloud.im.adapter.ext.neteaseSDK.util.NeteaseApiResponse;
import com.google.gson.Gson;

public abstract class Conversation {
    private final static Gson gson = new Gson();
    protected String from;
    protected String to;
    protected int ope;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getOpe() {
        return ope;
    }

    public void setOpe(int ope) {
        this.ope = ope;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public abstract NeteaseApiResponse send(Message message);

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
