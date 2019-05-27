package cn.rongcloud.im.adapter.ext.neteaseSDK.messages;

import cn.rongcloud.im.adapter.ext.neteaseSDK.util.MessageType;

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
     * @param msg
     */
    public PicMessage(String name, String md5, String url, String ext, int w, int h, int size) {
        this.type = MessageType.PIC.getCode();
        this.name = name;
        this.md5 = md5;
        this.url = url;
        this.ext = ext;
        this.w = w;
        this.h = h;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
