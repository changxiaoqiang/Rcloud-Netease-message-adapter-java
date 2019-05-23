package cn.rongcloud.im.adapter.libs.neteaseSDK.models;

import cn.rongcloud.im.adapter.libs.neteaseSDK.util.ConversationType;

public class PrivateConversation extends Conversation {
    public PrivateConversation(String from, String to) {
        this.from = from;
        this.to = to;
        this.ope = ConversationType.PRIVATE.getCode();
    }
}
