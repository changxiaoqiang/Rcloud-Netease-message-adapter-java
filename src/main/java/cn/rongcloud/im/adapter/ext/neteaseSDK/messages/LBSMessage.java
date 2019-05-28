package cn.rongcloud.im.adapter.ext.neteaseSDK.messages;

import cn.rongcloud.im.adapter.ext.neteaseSDK.util.MessageType;

public class LBSMessage extends Message {
    private String title;
    private double lng;
    private double lat;

    public LBSMessage(String title, double lat, double lng) {
        this.type = MessageType.LBS.getCode();
        this.title = title;
        this.lng = lng;
        this.lat = lat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
