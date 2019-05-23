package cn.rongcloud.im.adapter.libs.neteaseSDK.messages;

import com.google.gson.Gson;

import java.util.HashMap;

public abstract class Message {
    private final static Gson gson = new Gson();
    protected String from;
    protected int ope;
    protected String to;
    protected int type;
    protected HashMap<String, Object> body = new HashMap<>();

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public HashMap<String, Object> getBody() {
        return body;
    }

    public void setBody(HashMap<String, Object> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
