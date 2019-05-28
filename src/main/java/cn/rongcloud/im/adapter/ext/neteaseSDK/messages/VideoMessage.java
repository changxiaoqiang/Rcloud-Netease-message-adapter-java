package cn.rongcloud.im.adapter.ext.neteaseSDK.messages;

import cn.rongcloud.im.adapter.ext.neteaseSDK.util.MessageType;

public class VideoMessage extends Message {
    private int dur;
    private String md5;
    private String url;
    private int w;
    private int h;
    private String ext;
    private int size;

    public VideoMessage(int dur, String md5, String url, int w, int h, int size) {
        this.type = MessageType.VIDEO.getCode();
        this.dur = dur;
        this.md5 = md5;
        this.url = url;
        this.w = w;
        this.h = h;
        this.ext = "mp4";
        this.size = size;
    }

    public int getDur() {
        return dur;
    }

    public void setDur(int dur) {
        this.dur = dur;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public String getExt() {
        return ext;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
