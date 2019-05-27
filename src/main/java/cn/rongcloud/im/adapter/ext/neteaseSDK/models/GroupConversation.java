package cn.rongcloud.im.adapter.ext.neteaseSDK.models;

import cn.rongcloud.im.adapter.ext.neteaseSDK.messages.Message;
import cn.rongcloud.im.adapter.ext.neteaseSDK.util.NeteaseApiResponse;

public class GroupConversation extends Conversation {

    /**
     * 群会话
     * @param fromUserId
     * @param toGroupId
     */
    public GroupConversation(String fromUserId, String toGroupId) {
        this.from = fromUserId;
        this.to = toGroupId;
    }

    @Override
    public NeteaseApiResponse send(Message message) {
        return null;
    }
}
