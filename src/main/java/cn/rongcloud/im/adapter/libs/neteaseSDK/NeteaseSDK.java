package cn.rongcloud.im.adapter.libs.neteaseSDK;

import cn.rongcloud.im.adapter.libs.neteaseSDK.messages.Message;
import cn.rongcloud.im.adapter.libs.neteaseSDK.messages.TextMessage;
import cn.rongcloud.im.adapter.libs.neteaseSDK.util.NeteaseApiResponse;
import cn.rongcloud.im.adapter.libs.neteaseSDK.util.HttpUtil;

import java.net.HttpURLConnection;
import java.util.concurrent.ConcurrentHashMap;

public class NeteaseSDK {
    private static volatile NeteaseSDK instance = null;
    private String NeteaseAppkey;
    private String NeteaseSecret;
    private static ConcurrentHashMap<String, NeteaseSDK> neteaseSDKMap = new ConcurrentHashMap();

    public NeteaseSDK(String neteaseAppkey, String neteaseSecret) {
        NeteaseAppkey = neteaseAppkey;
        NeteaseSecret = neteaseSecret;
    }

    public static NeteaseSDK getInstance(String NeteaseAppkey, String NeteaseSecret) {
        if (null == neteaseSDKMap.get(NeteaseAppkey)) {
            instance = new NeteaseSDK(NeteaseAppkey, NeteaseSecret);
            neteaseSDKMap.putIfAbsent(NeteaseAppkey, instance);
        }
        return neteaseSDKMap.get(NeteaseAppkey);
    }

    public NeteaseApiResponse sendMessage(Message msg) throws Exception {
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(NeteaseAppkey, NeteaseSecret, "/nimserver/msg/sendMsg.action");
        HttpUtil.setBodyParameter(msg, conn);
        NeteaseApiResponse result = HttpUtil.returnResult(conn);
        return result;
    }

    public static void main(String[] args) throws Exception {
        NeteaseSDK neteaseSDK = NeteaseSDK.getInstance("", "");

        Message txtMsg = new TextMessage("1", "2", "哈哈");
        neteaseSDK.sendMessage(txtMsg);
    }

}
