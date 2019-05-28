package cn.rongcloud.im.adapter.ext.IMForward.Beans;

public class RcloudRouteMsgBean {
    private String fromUserId;
    private String toUserId;
    private String objectName;
    private String content;
    private String channelType;
    private String msgTimestamp;
    private String msgUID;
    private String[] groupUserIds;

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getMsgTimestamp() {
        return msgTimestamp;
    }

    public void setMsgTimestamp(String msgTimestamp) {
        this.msgTimestamp = msgTimestamp;
    }

    public String getMsgUID() {
        return msgUID;
    }

    public void setMsgUID(String msgUID) {
        this.msgUID = msgUID;
    }

    public String[] getGroupUserIds() {
        return groupUserIds;
    }

    public void setGroupUserIds(String[] groupUserIds) {
        this.groupUserIds = groupUserIds;
    }
}
