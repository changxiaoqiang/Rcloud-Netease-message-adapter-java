package cn.rongcloud.im.adapter.utils;

import cn.rongcloud.im.adapter.ext.neteaseSDK.util.CheckSumBuilder;
import io.rong.util.CodeUtil;

import javax.servlet.http.HttpServletRequest;

public class VerifyRequest {
    public static boolean verifyNetease(HttpServletRequest request, String neteaseSecret, String body) throws Exception {
        String CurTime = request.getHeader("CurTime");
        String CheckSum = request.getHeader("CheckSum");

        // 获取计算过的md5及checkSum
        String verifyMD5 = CheckSumBuilder.getMD5(body);
        String MD5 = request.getHeader("MD5");
        if (!verifyMD5.equals(MD5)) {
//            System.err.println(verifyMD5 + " is not equal " + MD5);
            return false;
        }
        String verifyChecksum = CheckSumBuilder.getCheckSum(neteaseSecret, verifyMD5, CurTime);
        if (!verifyChecksum.equals(CheckSum)) {
            return false;
        }

        return true;
    }

    public static boolean verifyRcloud(HttpServletRequest request, String rcloudSecret) throws Exception {
        String timeStamp = request.getParameter("signTimestamp");
        String nonce = request.getParameter("nonce");
        String sign = request.getParameter("signature");
        StringBuilder toSign = (new StringBuilder(rcloudSecret)).append(nonce).append(timeStamp);
        String reSign = CodeUtil.hexSHA1(toSign.toString());
        if (!reSign.equals(sign)) {
            return false;
        }
        return true;
    }
}
