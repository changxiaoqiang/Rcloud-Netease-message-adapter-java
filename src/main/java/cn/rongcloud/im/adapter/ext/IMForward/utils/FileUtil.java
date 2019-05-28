package cn.rongcloud.im.adapter.ext.IMForward.utils;

import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

public class FileUtil {
    public static String getMD5(InputStream inputStream) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] buffer = new byte[1024];
        int len;

        while ((len = inputStream.read(buffer, 0, 1024)) != -1) {
            digest.update(buffer, 0, len);
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        String md5 = bigInt.toString(16);
        return md5;
    }

}
