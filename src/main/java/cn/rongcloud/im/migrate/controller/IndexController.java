package cn.rongcloud.im.migrate.controller;

import cn.rongcloud.im.migrate.models.CheckSumBuilder;
import cn.rongcloud.im.migrate.response.ApiResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequestMapping("/index")
@RestController
public class IndexController {
    private final String appSecret = "7bb79g40f44j";

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ApiResponse index(HttpServletRequest request) throws IOException {
        ApiResponse response = new ApiResponse(200);
        try {
            // 获取请求体
            byte[] body = readBody(request);
            if (body == null) {
                response.setCode(414);
                return response;
            }
            // 获取 AppKey 在多应用场景下，根据 appKey 获取对应的 appSecret
            String AppKey = request.getHeader("AppKey");
            String CurTime = request.getHeader("CurTime");
            String MD5 = request.getHeader("MD5");
            String CheckSum = request.getHeader("CheckSum");

            // 将请求体转成String格式，并打印
            String requestBody = new String(body, "utf-8");
            // 获取计算过的md5及checkSum
            String verifyMD5 = CheckSumBuilder.getMD5(requestBody);
            String verifyChecksum = CheckSumBuilder.getCheckSum(appSecret, verifyMD5, CurTime);
            if (!verifyChecksum.equals(CheckSum)) {
                response.setCode(403);
                response.setMsg("Invalid request");
                return response;
            }

            return response;
        } catch (Exception ex) {
            response.setCode(414);
            response.setMsg(ex.getMessage());
            return response;
        }
    }

    private byte[] readBody(HttpServletRequest request) throws IOException {
        if (request.getContentLength() > 0) {
            byte[] body = new byte[request.getContentLength()];
            IOUtils.readFully(request.getInputStream(), body);
            return body;
        } else
            return null;
    }
}


