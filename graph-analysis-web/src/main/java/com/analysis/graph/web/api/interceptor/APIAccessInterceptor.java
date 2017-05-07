package com.analysis.graph.web.api.interceptor;

import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.util.LoggerUtils;
import com.analysis.graph.common.util.MaskSensitiveInfoUtils;
import com.analysis.graph.web.config.app.GraphAnalysisAPIProperties;
import com.analysis.graph.web.library.repository.SessionRepository;
import com.analysis.graph.web.library.service.APIAccessFrequencyManageService;
import com.analysis.graph.web.library.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private GraphAnalysisAPIProperties graphAnalysisAPIProperties;


    public boolean isApiDisabled(String requestUri) {
        if (CollectionUtils.isEmpty(graphAnalysisAPIProperties.getBlockedApiPatterns())) {
            return false;
        }
        for (Pattern pattern : graphAnalysisAPIProperties.getBlockedApiPatterns()) {
            Matcher matcher = pattern.matcher(requestUri);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }


    private boolean hasApiAuthorization(String requestUri) {
        for (String authApi : sessionRepository.getAuthorizedUser().getResources()) {
            if (requestUri.matches(authApi)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {
        String requestUri = request.getRequestURI();

        /*判断api是否失效*/
        if (isApiDisabled(requestUri)) {
            HttpUtils.writeResponse(response, HttpStatus.METHOD_NOT_ALLOWED, "该服务暂时不可用");
            return false;
        }

        /*判断用户访问的api是否已授权*/
        if (!hasApiAuthorization(requestUri)) {
            HttpUtils.writeResponse(response, HttpStatus.UNAUTHORIZED, "没有权限访问");
            return false;
        }

        /*统计api访问次数*/
        try {
            Client client = sessionRepository.getCurrentOnlineClient();
            if (apiAccessFrequencyManageService.increaseUserAccess(client.getMobile(), requestUri)) {
                LoggerUtils.builder(logger, "User limit reached for API {}, mobile: {}")
                        .addParams(requestUri, MaskSensitiveInfoUtils.maskMobile(client.getMobile()))
                        .addExtra(client.toString())
                        .warn();
                HttpUtils.writeResponse(response, HttpStatus.TOO_MANY_REQUESTS, "操作过于频繁");
                return false;
            }
        } catch (IllegalArgumentException e) {
            String ip = request.getRemoteAddr();
            if (apiAccessFrequencyManageService.increaseIpAccess(ip, requestUri)) {
                LoggerUtils.builder(logger, "Ip limit reached for API {}, ip: {}")
                        .addParams(requestUri, ip)
                        .warn();
                HttpUtils.writeResponse(response, HttpStatus.TOO_MANY_REQUESTS, "操作过于频繁");
                return false;
            }
        }
        return true;
    }


}
