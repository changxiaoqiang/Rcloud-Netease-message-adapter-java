package cn.rongcloud.im.adapter.libs;

import cn.rongcloud.im.adapter.libs.neteaseSDK.NeteaseSDK;
import cn.rongcloud.im.adapter.libs.neteaseSDK.messages.Message;
import cn.rongcloud.im.adapter.libs.neteaseSDK.messages.TextMessage;
import cn.rongcloud.im.adapter.libs.neteaseSDK.models.Conversation;
import cn.rongcloud.im.adapter.libs.neteaseSDK.util.ConversationType;
import cn.rongcloud.im.adapter.libs.neteaseSDK.util.NeteaseApiResponse;
import com.google.gson.Gson;
import io.rong.RongCloud;
import io.rong.messages.BaseMessage;
import io.rong.messages.TxtMessage;
import io.rong.models.message.MessageModel;
import io.rong.models.message.PrivateMessage;
import io.rong.models.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;

public class IMForward {
    private static final Gson gson = new Gson();

    public static ResponseResult neteaseToRcloud(String body, String rcloudKey, String rcloudSecret) throws Exception {
        NeteaseRouteMsgBean routeMsg = gson.fromJson(body, NeteaseRouteMsgBean.class);

        RongCloud rongCloud = RongCloud.getInstance(rcloudKey, rcloudSecret);
        String fromUser = routeMsg.getFromAccount();
        String toUser = routeMsg.getTo();
        String convType = routeMsg.getConvType();
        String msgType = routeMsg.getMsgType();
        BaseMessage message = null;

        ResponseResult responseResult = null;
        switch (msgType) {
            case "TEXT":
                message = new TxtMessage(routeMsg.getBody(), routeMsg.getAttach());
                break;
        }
        switch (convType) {
            case "PERSON":
                MessageModel conv = new PrivateMessage();
                conv.setSenderId(fromUser);
                conv.setTargetId(new String[]{toUser});
                conv.setContent(message);
                conv.setObjectName(message.getType());
                responseResult = rongCloud.message.msgPrivate.send((PrivateMessage) conv);
                break;
        }
        return responseResult;
    }

    public static NeteaseApiResponse rcloudToNetease(HttpServletRequest request, String neteaseKey, String neteaseSecret) throws Exception {
        RcloudRouteMsgBean routeMsg = new RcloudRouteMsgBean();
        routeMsg.setFromUserId(request.getParameter("fromUserId"));
        routeMsg.setToUserId(request.getParameter("toUserId"));
        routeMsg.setChannelType(request.getParameter("channelType"));
        routeMsg.setContent(request.getParameter("content"));
        routeMsg.setMsgTimestamp(request.getParameter("msgTimestamp"));
        routeMsg.setMsgUID(request.getParameter("msgUID"));
        routeMsg.setGroupUserIds(new String[]{request.getParameter("groupUserIds")});
        routeMsg.setObjectName(request.getParameter("objectName"));

        NeteaseSDK neteaseSDK = new NeteaseSDK(neteaseKey, neteaseSecret);
        BaseMessage message = null;
        NeteaseApiResponse responseResult = null;
        String fromUserId = routeMsg.getFromUserId();
        String toUserId = routeMsg.getToUserId();
        String content = routeMsg.getContent();

        Message netMsg = null;

        switch(routeMsg.getObjectName()) {
            case "RC:TxtMsg":
                message = gson.fromJson(content, TxtMessage.class);
                netMsg = new TextMessage(fromUserId, toUserId, ((TxtMessage) message).getContent());
                break;
        }

        switch (routeMsg.getChannelType()) {
            case "PERSON":
                netMsg.setOpe(ConversationType.PRIVATE.getCode());
                break;
            case "GROUP":
                netMsg.setOpe(ConversationType.GROUP.getCode());
                break;
        }
        responseResult = neteaseSDK.sendMessage(netMsg);
        return responseResult;
    }
}
