package cn.rongcloud.im.adapter.ext.IMForward;

import cn.rongcloud.im.adapter.ext.IMForward.Beans.ImgBean;
import cn.rongcloud.im.adapter.ext.IMForward.Beans.NeteaseRouteMsgBean;
import cn.rongcloud.im.adapter.ext.IMForward.Beans.RcloudRouteMsgBean;
import cn.rongcloud.im.adapter.ext.IMForward.utils.ImgUtil;
import cn.rongcloud.im.adapter.ext.neteaseSDK.NeteaseSDK;
import cn.rongcloud.im.adapter.ext.neteaseSDK.messages.LocMessage;
import cn.rongcloud.im.adapter.ext.neteaseSDK.messages.Message;
import cn.rongcloud.im.adapter.ext.neteaseSDK.messages.PicMessage;
import cn.rongcloud.im.adapter.ext.neteaseSDK.messages.TextMessage;
import cn.rongcloud.im.adapter.ext.neteaseSDK.models.Conversation;
import cn.rongcloud.im.adapter.ext.neteaseSDK.models.GroupConversation;
import cn.rongcloud.im.adapter.ext.neteaseSDK.models.PrivateConversation;
import cn.rongcloud.im.adapter.ext.neteaseSDK.util.NeteaseApiResponse;
import com.google.gson.Gson;
import io.rong.RongCloud;
import io.rong.messages.BaseMessage;
import io.rong.messages.ImgMessage;
import io.rong.messages.LBSMessage;
import io.rong.messages.TxtMessage;
import io.rong.models.message.GroupMessage;
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
        Message neteaseMsg = null;

        ResponseResult responseResult = null;
        switch (msgType) {
            default:
            case "TEXT":
                message = new TxtMessage(routeMsg.getBody(), routeMsg.getAttach());
                break;
            case "PICTURE":
                neteaseMsg = gson.fromJson(routeMsg.getAttach(), PicMessage.class);
                message = new ImgMessage("", "", ((PicMessage) neteaseMsg).getUrl());
                break;
            case "LOCATION":
                neteaseMsg = gson.fromJson(routeMsg.getAttach(), LocMessage.class);
                message = new LBSMessage("", "", ((LocMessage) neteaseMsg).getLat(), ((LocMessage) neteaseMsg).getLng(), ((LocMessage) neteaseMsg).getTitle());
                break;

        }
        MessageModel conv = null;
        switch (convType) {
            default:
            case "PERSON":
                conv = new PrivateMessage();
                conv.setSenderId(fromUser);
                conv.setTargetId(new String[]{toUser});
                conv.setContent(message);
                conv.setObjectName(message.getType());
                responseResult = rongCloud.message.msgPrivate.send((PrivateMessage) conv);
                break;
            case "GROUP":
                conv = new GroupMessage();
                conv.setSenderId(fromUser);
                conv.setTargetId(new String[]{toUser});
                conv.setContent(message);
                conv.setObjectName(message.getType());
                responseResult = rongCloud.message.group.send((GroupMessage) conv);
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
        BaseMessage rcloudMsg = null;
        NeteaseApiResponse responseResult = null;
        String fromUserId = routeMsg.getFromUserId();
        String toUserId = routeMsg.getToUserId();
        String content = routeMsg.getContent();

        Message neteaseMsg = null;
        Conversation conversation = null;

        switch (routeMsg.getObjectName()) {
            default:
            case "RC:TxtMsg":
                rcloudMsg = gson.fromJson(content, TxtMessage.class);
                neteaseMsg = new TextMessage(((TxtMessage) rcloudMsg).getContent());
                break;
            case "RC:ImgMsg":
                rcloudMsg = gson.fromJson(content, ImgMessage.class);
                String imgUrl = ((ImgMessage) rcloudMsg).getImageUri();
                ImgBean imgBean = ImgUtil.getImgBean(imgUrl);
                neteaseMsg = new PicMessage("图片消息", imgBean.getMd5(), imgUrl, imgBean.getExt(), imgBean.getW(), imgBean.getH(), imgBean.getSize());
                break;
            case "RC:LBSMsg":
                rcloudMsg = gson.fromJson(content, LBSMessage.class);
                LBSMessage rLBSMessage = (LBSMessage) rcloudMsg;
                neteaseMsg = new LocMessage(rLBSMessage.getPoi(), rLBSMessage.getLatitude(), rLBSMessage.getLongitude());
                break;
        }

        switch (routeMsg.getChannelType()) {
            default:
            case "PERSON":
                conversation = new PrivateConversation(fromUserId, toUserId);
                break;
            case "GROUP":
                conversation = new GroupConversation(fromUserId, toUserId);
                break;
        }
        responseResult = neteaseSDK.sendMessage(conversation, neteaseMsg);
        return responseResult;
    }
}
