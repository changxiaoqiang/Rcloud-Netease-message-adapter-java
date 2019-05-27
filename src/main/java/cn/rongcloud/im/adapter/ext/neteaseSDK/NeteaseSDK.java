package cn.rongcloud.im.adapter.ext.neteaseSDK;

import cn.rongcloud.im.adapter.ext.neteaseSDK.messages.Message;
import cn.rongcloud.im.adapter.ext.neteaseSDK.messages.PicMessage;
import cn.rongcloud.im.adapter.ext.neteaseSDK.messages.TextMessage;
import cn.rongcloud.im.adapter.ext.neteaseSDK.models.Conversation;
import cn.rongcloud.im.adapter.ext.neteaseSDK.models.PrivateConversation;
import cn.rongcloud.im.adapter.ext.neteaseSDK.util.NeteaseApiResponse;
import cn.rongcloud.im.adapter.ext.neteaseSDK.util.HttpUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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

    public NeteaseApiResponse sendMessage(Conversation conversation, Message msg) throws Exception {
        HttpURLConnection conn = HttpUtil.CreatePostHttpConnection(NeteaseAppkey, NeteaseSecret, "/nimserver/msg/sendMsg.action");
        HttpUtil.setBodyParameter(conn, conversation, msg);
        NeteaseApiResponse result = HttpUtil.returnResult(conn);
        return result;
    }

    public static void main(String[] args) throws Exception {
        NeteaseSDK neteaseSDK = NeteaseSDK.getInstance("", "");
        URL picUrl = new URL("https://yx-web-nosdn.netease.im/quickhtml%2Fassets%2Fyunxin%2Fdefault%2F111.png");
        URLConnection conn = picUrl.openConnection();
        int size = conn.getContentLength();

        InputStream input = conn.getInputStream();
        System.out.println(size);

        ImageIO.read(input);

        BufferedImage image = ImageIO.read(picUrl);
        System.out.println(image.getHeight());
        System.out.println(image.getWidth());
        System.out.println(image.getType());
        System.out.println(size);
        input.close();

        Message txtMsg = new TextMessage("哈哈");

        PicMessage picMessage = new PicMessage("图片", "", picUrl.toString(), "png", image.getWidth(), image.getHeight(), size);

        Conversation priConversation = new PrivateConversation("1", "2");
        System.out.println(neteaseSDK.sendMessage(priConversation, picMessage).getCode());
    }

}
