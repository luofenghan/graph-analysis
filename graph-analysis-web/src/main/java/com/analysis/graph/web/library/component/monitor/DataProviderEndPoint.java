package com.analysis.graph.web.library.component.monitor;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by cwc on 2017/4/19 0019.
 */
@ConfigurationProperties(prefix = "endpoints.custom.dataprovider")
public class DataProviderEndPoint extends AbstractEndpoint<DataProviderEndPoint.DataProviderStatus> {


    public DataProviderEndPoint() {
        super("dataprovider");
    }

    @Override
    public DataProviderStatus invoke() {
        return null;
    }

    static class DataProviderStatus {

    }
}
