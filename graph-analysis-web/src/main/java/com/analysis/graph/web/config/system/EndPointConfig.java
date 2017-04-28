package com.analysis.graph.web.config.system;

import com.analysis.graph.web.library.component.monitor.DataProviderEndPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cwc on 2017/4/19 0019.
 */
@Configuration
public class EndPointConfig {
    @Bean
    public DataProviderEndPoint statusEndPoint() {
        return new DataProviderEndPoint();
    }
}
