package cn.rongcloud.im.adapter.controller;

import cn.rongcloud.im.adapter.ext.IMForward.IMForward;
import cn.rongcloud.im.adapter.ext.neteaseSDK.util.NeteaseApiResponse;
import cn.rongcloud.im.adapter.response.ApiResponse;
import cn.rongcloud.im.adapter.utils.VerifyRequest;
import io.rong.models.response.ResponseResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequestMapping("/index")
@RestController
public class IndexController {
    private final String NeteaseKey = "";
    private final String NeteaseSecret = "";

    private final String RcloudKey = "";
    private final String RcloudSecret = "";


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ApiResponse home() {
        return new ApiResponse(200, "OK");
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ApiResponse index(HttpServletRequest request) throws Exception {
        //TODO 业务处理存储历史记录和判断接收者使用的是否是旧客户端(是否需要转发)

        ApiResponse response = new ApiResponse(200);

        String rcloudKey = request.getParameter("appKey");

        if (!StringUtils.isEmpty(rcloudKey)) {
            response = this.rcloudToNetease(request);
        } else {
            response = this.neteaseToRcloud(request);
        }

        return response;
    }

    /**
     * 网易云信转融云
     *
     * @param request
     * @throws Exception
     */
    private ApiResponse neteaseToRcloud(HttpServletRequest request) throws Exception {
        ApiResponse response = new ApiResponse(200);
        String appKey = request.getHeader("AppKey");
        //TODO 根据 appKey 获取 secret，处理多应用场景

        // 获取请求体
        byte[] body = readBody(request);
        String reqBody = new String(body, "UTF-8");

        if (body == null) {
            response.setCode(403);
            System.out.println(response);
            return response;
        }

        Boolean verify = VerifyRequest.verifyNetease(request, NeteaseSecret, reqBody);
        if (!verify) {
            response.setCode(403);
            System.out.println(response);
//            return response;
        }

        ResponseResult result = IMForward.neteaseToRcloud(reqBody, RcloudKey, RcloudSecret);

        response.setCode(result.getCode());
        response.setMsg(result.getErrorMessage());
        return response;
    }

    /**
     * 融云转网易云信
     *
     * @param request
     * @throws Exception
     */
    private ApiResponse rcloudToNetease(HttpServletRequest request) throws Exception {
        ApiResponse response = new ApiResponse(200);

        String appKey = request.getParameter("appKey");
        Boolean verify = VerifyRequest.verifyRcloud(request, RcloudSecret);
        if (!verify) {
            response.setCode(403);
            return response;
        }
        //TODO 根据 appKey 获取 secret，处理多应用场景

        NeteaseApiResponse result = IMForward.rcloudToNetease(request, NeteaseKey, NeteaseSecret);

        response.setCode(result.getCode());
        response.setMsg(result.getMsg());

        return response;
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


