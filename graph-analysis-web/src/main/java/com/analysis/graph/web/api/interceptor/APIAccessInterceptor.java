package com.analysis.graph.web.api.interceptor;

import com.alibaba.fastjson.JSON;
import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dto.ErrorDTO;
import com.analysis.graph.common.util.LoggerUtils;
import com.analysis.graph.common.util.MaskSensitiveInfoUtils;
import com.analysis.graph.web.library.repository.ClientRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import com.analysis.graph.web.library.service.APIAccessFrequencyManageService;
import com.analysis.graph.web.library.service.APIAddressableService;
import com.analysis.graph.web.library.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by cwc on 16/7/26.
 */
@Component
public class APIAccessInterceptor extends HandlerInterceptorAdapter {
    private final Logger logger = LoggerFactory.getLogger(APIAccessInterceptor.class);

    @Resource
    private SessionRepository sessionRepository;

    @Resource
    private APIAccessFrequencyManageService apiAccessFrequencyManageService;

    @Resource
    private APIAddressableService apiAddressableService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        //get request uri
        String requestUri = request.getRequestURI();
        if (apiAddressableService.isApiDisabled(requestUri)) {
            writeApiNotAvailableResponse(response);
            return false;
        }
        try {
            Client client = sessionRepository.getCurrentOnlineClient();
            boolean userOverLimit = apiAccessFrequencyManageService.increaseUserAccess(client.getMobile(), requestUri);
            if (userOverLimit) {
                LoggerUtils.builder(logger, "User limit reached for API {}, mobile: {}")
                        .addParams(requestUri, MaskSensitiveInfoUtils.maskMobile(client.getMobile()))
                        .addExtra(client.toString())
                        .warn();
                writeTwoManyRequestsResponse(response);
                return false;
            }
        } catch (IllegalStateException e) {
            String ip = request.getRemoteAddr();
            boolean ipOverLimit = apiAccessFrequencyManageService.increaseIpAccess(ip, requestUri);
            if (ipOverLimit) {
                LoggerUtils.builder(logger, "Ip limit reached for API {}, ip: {}")
                        .addParams(requestUri, ip)
                        .warn();
                writeTwoManyRequestsResponse(response);
                return false;
            }
        }
        return true;
    }

    private void writeApiNotAvailableResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.METHOD_NOT_ALLOWED.toString());
        errorDTO.setTitle("该服务暂时不可用");
        response.getWriter().write(JSON.toJSONString(errorDTO));
    }

    private void writeTwoManyRequestsResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.TOO_MANY_REQUESTS.toString());
        errorDTO.setTitle("操作过于频繁");
        response.getWriter().write(JSON.toJSONString(errorDTO));
    }
}
