package cn.rongcloud.im.adapter.ext.neteaseSDK.models;

import cn.rongcloud.im.adapter.ext.neteaseSDK.messages.Message;
import cn.rongcloud.im.adapter.ext.neteaseSDK.util.ConversationType;
import cn.rongcloud.im.adapter.ext.neteaseSDK.util.NeteaseApiResponse;
import org.apache.commons.lang3.StringUtils;

public class PrivateConversation extends Conversation {
    public PrivateConversation(String from, String to) {
        this.from = from;
        this.to = to;
        this.ope = ConversationType.PRIVATE.getCode();
    }

    @Override
    public NeteaseApiResponse send(Message message) {
        if (StringUtils.isEmpty(from) || StringUtils.isEmpty(to) || null == message) {
            return new NeteaseApiResponse(401, "请求参数错误");
        }

        return new NeteaseApiResponse(200);
    }
}
