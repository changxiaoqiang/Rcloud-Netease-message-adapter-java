package cn.rongcloud.im.adapter.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private static Gson gson = new Gson();

    private int code;
    private String msg;

    public ApiResponse() {
    }

    public ApiResponse(int code) {
        this.code = code;
    }

    public ApiResponse(int code, String msg) {
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

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
