package cn.rongcloud.im.adapter.ext.neteaseSDK.util;

import java.util.HashMap;

public class NeteaseApiResponse {
    private int code;
    private HashMap<String, Object> data;
    private String desc;

    public NeteaseApiResponse(int code) {
        this.code = code;
    }

    public NeteaseApiResponse(int code, HashMap<String, Object> data) {
        this.code = code;
        this.data = data;
    }

    public NeteaseApiResponse(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}
