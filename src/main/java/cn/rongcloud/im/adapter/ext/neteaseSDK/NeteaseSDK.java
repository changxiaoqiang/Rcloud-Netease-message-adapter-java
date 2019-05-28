package cn.rongcloud.im.adapter.ext.neteaseSDK;

import cn.rongcloud.im.adapter.ext.IMForward.Beans.ImgBean;
import cn.rongcloud.im.adapter.ext.IMForward.utils.FileUtil;
import cn.rongcloud.im.adapter.ext.IMForward.utils.ImgUtil;
import cn.rongcloud.im.adapter.ext.neteaseSDK.messages.*;
import cn.rongcloud.im.adapter.ext.neteaseSDK.models.Conversation;
import cn.rongcloud.im.adapter.ext.neteaseSDK.models.PrivateConversation;
import cn.rongcloud.im.adapter.ext.neteaseSDK.util.NeteaseApiResponse;
import cn.rongcloud.im.adapter.ext.neteaseSDK.util.HttpUtil;

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
        // 验证云信并没有验证文件 MD5 应该是在客户端验证，如果客户端没做这个验证的可以忽略 MD5 计算
        // 文件属性也是在客户端使用的，客户端处理没问题的话，可以忽略提取属性
        String picUrl = "https://yx-web-nosdn.netease.im/quickhtml%2Fassets%2Fyunxin%2Fdefault%2F111.png";
        String videoUrl = "http://nimtest.nos.netease.com/21f34447-e9ac-4871-91ad-d9f03af20412";
        ImgBean imgBean = ImgUtil.getImgBean(picUrl);

        Message txtMsg = new TextMessage("哈哈");

        PicMessage picMessage = new PicMessage("图片", imgBean.getMd5(), picUrl, imgBean.getExt(), imgBean.getW(), imgBean.getH(), imgBean.getSize());


        URLConnection conn = new URL(videoUrl).openConnection();
        InputStream input = conn.getInputStream();

        VideoMessage videoMessage = new VideoMessage(5, FileUtil.getMD5(input), videoUrl, 100, 200, conn.getContentLength());

        LocMessage locMessage = new LocMessage("天安门", 39.542637, 116.232922);

        FileMessage fileMessage = new FileMessage("file.txt", FileUtil.getMD5(input),"http://nimtest.nos.netease.com/21f34447-e9ac-4871-91ad-d9f03af20412", "mp4", conn.getContentLength());

        Conversation priConversation = new PrivateConversation("1", "2");
        System.out.println(neteaseSDK.sendMessage(priConversation, fileMessage));
    }

}
