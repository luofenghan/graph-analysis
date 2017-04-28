package com.analysis.graph.web.config.app;

import com.google.common.base.Preconditions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by cwc on 2017/4/18 0018.
 */
@Configuration
@ConfigurationProperties(prefix = "graph.analysis.api")
public class GraphAnalysisAPIProperties {
    private List<Pattern> blocked;

    public void setBlocked(List<String> blocked) {
        Preconditions.checkArgument(!CollectionUtils.isEmpty(blocked));
        if (!StringUtils.isEmpty(blocked)) {
            this.blocked = new ArrayList<>();
            //API设置以逗号隔开
            for (String apiRegex : blocked) {
                this.blocked.add(Pattern.compile(apiRegex.trim()));
            }
        }
    }

    public List<Pattern> getBlockedApiPatterns() {
        return blocked;
    }
}
