package com.analysis.graph.web.library.util;

import com.analysis.graph.common.domain.dto.ErrorDTO;
import org.springframework.http.HttpStatus;

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

    public static void writeResponse(HttpServletResponse response, HttpStatus httpStatus, String title) throws IOException {
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(httpStatus.getReasonPhrase());
        errorDTO.setCode(httpStatus.value());
        errorDTO.setTitle(title);
        errorDTO.setDetail("");
        response.getWriter().write(JsonUtils.toJsonString(errorDTO));
    }

}
