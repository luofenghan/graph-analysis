package com.analysis.graph.web.library.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cwc on 2017/4/9 0009.
 */
public class HttpUtils {

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public static void writeAjaxSuccessRequestResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        JSONObject authSuccess = new JSONObject();
        authSuccess.put("success", true);
        authSuccess.put("status", "1");
        response.getWriter().write(authSuccess.toJSONString());
    }

}
