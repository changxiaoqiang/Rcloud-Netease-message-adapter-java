package cn.rongcloud.im.adapter.ext.neteaseSDK.messages;

import cn.rongcloud.im.adapter.ext.neteaseSDK.util.MessageType;

public class FileMessage extends Message {
    private String name;
    private String md5;
    private String url;
    private String ext;
    private int size;

    public FileMessage(String name, String md5, String url, String ext, int size) {
        this.type = MessageType.FILE.getCode();
        this.name = name;
        this.md5 = md5;
        this.url = url;
        this.ext = ext;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
