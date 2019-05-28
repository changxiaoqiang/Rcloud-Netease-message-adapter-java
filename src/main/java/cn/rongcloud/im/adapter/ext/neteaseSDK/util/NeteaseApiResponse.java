package cn.rongcloud.im.adapter.ext.neteaseSDK.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;

import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NeteaseApiResponse {
    private final static Gson gson = new Gson();
    private int code;
    private HashMap<String, Object> data;
    private String msg;

    public NeteaseApiResponse(int code) {
        this.code = code;
    }

    public NeteaseApiResponse(int code, HashMap<String, Object> data) {
        this.code = code;
        this.data = data;
    }

    public NeteaseApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
