package cn.rongcloud.im.adapter.libs.neteaseSDK.util;

import cn.rongcloud.im.adapter.libs.neteaseSDK.messages.Message;
import com.google.gson.Gson;

import javax.net.ssl.*;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class HttpUtil {
    private static final String API_URL = "http://api.netease.im";
    private static final String APPKEY = "AppKey";
    private static final String NONCE = "Nonce";
    private static final String CurTime = "CurTime";
    private static final String CheckSum = "CheckSum";
    private static SSLContext sslCtx = null;
    private static final Gson gson = new Gson();

    static {
        try {
            sslCtx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslCtx.init(null, new TrustManager[]{tm}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                // TODO Auto-generated method stub
                return true;
            }

        });

        HttpsURLConnection.setDefaultSSLSocketFactory(sslCtx.getSocketFactory());

    }

    // 设置body体
    public static void setBodyParameter(StringBuilder sb, HttpURLConnection conn) throws IOException {
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.writeBytes(sb.toString());
        out.flush();
        out.close();
    }

    public static HttpURLConnection CreateGetHttpConnection(String uri) throws MalformedURLException, IOException {
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(30000);
        conn.setRequestMethod("GET");
        return conn;
    }

    public static void setBodyParameter(Object obj, HttpURLConnection conn) throws Exception {
        String params = getParams(obj);
        System.out.println(params);
        setBodyParameter(params, conn);
    }

    public static void setBodyParameter(String str, HttpURLConnection conn) throws IOException {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(conn.getOutputStream());
            out.write(str.getBytes("utf-8"));
            out.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }

    }

    public static HttpURLConnection CreatePostHttpConnection(String appKey, String appSecret, String action) throws IOException {
        return CreatePostHttpConnection(appKey, appSecret, action, "application/x-www-form-urlencoded");
    }

    public static HttpURLConnection CreatePostHttpConnection(String appKey, String appSecret, String action,
                                                             String contentType) throws IOException {
        String nonce = String.valueOf(Math.random() * 1000000);
        String curTime = String.valueOf(System.currentTimeMillis() / 1000);
        String sign = CheckSumBuilder.getCheckSum(appSecret, nonce, curTime);
        String uri = API_URL + action;
        URL url = new URL(uri);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setInstanceFollowRedirects(true);
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);

        conn.setRequestProperty(APPKEY, appKey);
        conn.setRequestProperty(NONCE, nonce);
        conn.setRequestProperty(CurTime, curTime);
        conn.setRequestProperty(CheckSum, sign);
        conn.setRequestProperty("Content-Type", contentType);

        return conn;
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }

    /**
     * 将对象转为 urlParams
     * @param clazz
     * @return
     * @throws Exception
     */
    public static String getParams(Object clazz) throws Exception {
        List<Field> fields = new ArrayList<>();
        Class tmpClass = clazz.getClass();

        while (tmpClass != null && Message.class.isAssignableFrom(tmpClass)) {
            fields.addAll(Arrays.asList(tmpClass.getDeclaredFields()));
            tmpClass = tmpClass.getSuperclass();
        }

        StringBuilder requestURL = new StringBuilder();
        boolean flag = true;
        String property, value;
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            String modify = Modifier.toString(field.getModifiers());
            if (modify.indexOf("final") > -1) {
                continue;
            }

            // 允许访问私有变量
            field.setAccessible(true);
            // 属性名
            property = field.getName();
            // 属性值
            if (HashMap.class.isAssignableFrom(field.getType())) {
                value = gson.toJson(field.get(clazz));
            } else {
                value = field.get(clazz).toString();
            }

            //TODO 删除
            System.out.println(property + ":" + value);
            String params = property + "=" + value;

            if (flag) {
                requestURL.append(params);
                flag = false;
            } else {
                requestURL.append("&" + params);
            }
        }

        return requestURL.toString();
    }

    public static NeteaseApiResponse returnResult(HttpURLConnection conn) throws Exception {
        InputStream input = null;
        if (conn.getResponseCode() == 200) {
            input = conn.getInputStream();
        } else {
            input = conn.getErrorStream();
        }
        String result = new String(readInputStream(input), "UTF-8");
        return gson.fromJson(result, NeteaseApiResponse.class);
    }

}
