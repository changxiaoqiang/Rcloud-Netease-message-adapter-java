package cn.rongcloud.im.adapter.ext.IMForward.utils;

import cn.rongcloud.im.adapter.ext.IMForward.Beans.ImgBean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ImgUtil {

    public static ImgBean getImgBean(String imgUrl) throws Exception {
        ImgBean bean = new ImgBean();
        InputStream input = null;
        try {
            URL picUrl = new URL(imgUrl);
            HttpURLConnection conn = (HttpURLConnection) picUrl.openConnection();
            int size = conn.getContentLength();

            input = new BufferedInputStream(conn.getInputStream());
            String mimeType = URLConnection.guessContentTypeFromStream(input);

            String md5 = FileUtil.getMD5(input);


            ImageIO.read(input);
            BufferedImage image = ImageIO.read(picUrl);

            bean.setExt(FileTypeUtil.getExt(mimeType));
            bean.setW(image.getWidth());
            bean.setH(image.getHeight());
            bean.setMd5(md5);
            bean.setSize(size);

            input.close();
        } catch (Exception ex) {
            throw ex;
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                throw e;
            }
        }
        return bean;
    }


}
