package com.analysis.graph.web.library.service;

import com.analysis.graph.web.config.app.GraphAnalysisAPIProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 曹文成 on 16/7/30.
 */
@Service
public class APIAddressableService {
    private static final Logger logger = LoggerFactory.getLogger(APIAddressableService.class);

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

}
