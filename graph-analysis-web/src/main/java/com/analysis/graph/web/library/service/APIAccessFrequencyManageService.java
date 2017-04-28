package com.analysis.graph.web.library.service;

import com.analysis.graph.common.repository.CacheRepository;
import com.analysis.graph.common.util.LoggerUtils;
import com.analysis.graph.common.util.MaskSensitiveInfoUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xianghuidong on 16/7/26.
 */
@Service
public class APIAccessFrequencyManageService {
    private static final String CACHE_NAME = "rateLimitCache";

    private static final Logger logger = LoggerFactory.getLogger(APIAccessFrequencyManageService.class);

    private List<AccessFrequencyConfig> accessRateLimits;

    private ArrayList<ArrayList<AbstractMap.SimpleEntry<String, Pattern>>> compiledRegexLists;

    static class AccessFrequencyConfig {
        private String name;
        private String[] path;
        private int userLimit;
        private int ipLimit;
        private int interval;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getPath() {
            return path;
        }

        public void setPath(String[] path) {
            this.path = path;
        }

        int getUserLimit() {
            return userLimit;
        }

        public void setUserLimit(int userLimit) {
            this.userLimit = userLimit;
        }

        int getIpLimit() {
            return ipLimit;
        }

        public void setIpLimit(int ipLimit) {
            this.ipLimit = ipLimit;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }
    }

    @Resource
    private CacheRepository ehCacheRepository;

    @PostConstruct
    private void init() {
        //load rate limit config
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                this.getClass().getClassLoader().getResourceAsStream("rate-limit.json"), Charset.forName("UTF-8")));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        try {
            accessRateLimits = mapper.readValue(reader, new TypeReference<List<AccessFrequencyConfig>>() {
            });
            compiledRegexLists = new ArrayList<>();
            for (AccessFrequencyConfig accessRate : accessRateLimits) {
                ArrayList<AbstractMap.SimpleEntry<String, Pattern>> compiledRegexList = new ArrayList<>();
                for (String apiPath : accessRate.getPath()) {
                    Pattern pattern = Pattern.compile(apiPath, Pattern.CASE_INSENSITIVE);
                    AbstractMap.SimpleEntry<String, Pattern> apiRegex = new AbstractMap.SimpleEntry<>(apiPath, pattern);
                    compiledRegexList.add(apiRegex);
                }
                compiledRegexLists.add(compiledRegexList);
            }

        } catch (IOException ioEx) {
            LoggerUtils.builder(logger)
                    .format("failed to init accessRateLimits. ")
                    .addExtra(Throwables.getStackTraceAsString(ioEx))
                    .error();
        }
    }

    public boolean increaseUserAccess(String mobile, String requestUri) {
        if (accessRateLimits == null) {
            //not config
            return false;
        }
        for (int i = 0; i < compiledRegexLists.size(); i++) {
            ArrayList<AbstractMap.SimpleEntry<String, Pattern>> compiledRegexList = compiledRegexLists.get(i);

            for (AbstractMap.SimpleEntry<String, Pattern> apiPathMap : compiledRegexList) {
                Matcher matcher = apiPathMap.getValue().matcher(requestUri);
                if (matcher.find()) {
                    try {
                        AccessFrequencyConfig accessRate = accessRateLimits.get(i);
                        //用户访问限制定义在数组第一个位置, 参考rate-limit.json
                        return isOverVisitLimit(mobile, apiPathMap.getKey(), accessRate.getUserLimit(),
                                accessRate.getInterval());
                    } catch (RuntimeException rEx) {
                        LoggerUtils.builder(logger)
                                .format("failed to increase rate limit for api {}, request: {}, mobile: {}.")
                                .addParams(apiPathMap.getKey(), requestUri, MaskSensitiveInfoUtils.maskMobile(mobile))
                                .addExtra(Throwables.getStackTraceAsString(rEx))
                                .error();
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public boolean increaseIpAccess(String ip, String requestUri) {
        if (accessRateLimits == null) {
            //not config
            return false;
        }
        for (int i = 0; i < compiledRegexLists.size(); i++) {
            for (AbstractMap.SimpleEntry<String, Pattern> apiPathMap : compiledRegexLists.get(i)) {
                Matcher matcher = apiPathMap.getValue().matcher(requestUri);
                if (matcher.find()) {
                    try {
                        AccessFrequencyConfig accessRate = accessRateLimits.get(i);
                        //Ip访问限制定义在数组第二个位置, 参考rate-limit.json
                        return isOverVisitLimit(ip, apiPathMap.getKey(), accessRate.getIpLimit(),
                                accessRate.getInterval());
                    } catch (RuntimeException rEx) {
                        LoggerUtils.builder(logger)
                                .format("failed to increase rate limit for api {}, request: {}, ip: {}.")
                                .addParams(apiPathMap.getKey(), requestUri, ip)
                                .addExtra(Throwables.getStackTraceAsString(rEx))
                                .error();
                        return false; //不打断正常业务
                    }
                }
            }
        }
        return false;
    }

    private boolean isOverVisitLimit(String visitor, String apiPath, int upperLimit, int intervalInSeconds) {
        //key is: mobile or ip + "###" + matched path
        //for example: 13800138000###/api/reset-password or 10.1.192.168###/api/reset-password
        String key = String.format("%s###%s", visitor, apiPath);
        Element existingElement = null;
        try {
            existingElement = ehCacheRepository.getCacheElement(CACHE_NAME, key);
        } catch (RuntimeException ex) {
            //正常情况下即使不存在缓存, 也是应该返回空
            LoggerUtils.builder(logger, "failed to get cache element from {}, key is {}")
                    .addParams(CACHE_NAME, key)
                    .error();
        }
        if (existingElement == null) {
            //insert: 说明从未访问过或者缓存已经失效
            //这里的缓存对象"0"暂时没有任何作用, 我们只参考缓存对象的生命周期和访问次数来决定该API是否超限
            ehCacheRepository.addOrUpdateCacheObject(CACHE_NAME, key, 0, intervalInSeconds, intervalInSeconds);
        } else {
            //检查缓存命中次数, 如果超出限制则返回超限
            if (existingElement.getHitCount() >= upperLimit) {
                LoggerUtils.builder(logger)
                        .format("visitor {} has reached access limit for api {}. limit is {}, interval is {}")
                        .addParams(visitor, apiPath, upperLimit, intervalInSeconds)
                        .warn();
                return true;
            }
        }
        return false;
    }
}
