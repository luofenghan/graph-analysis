package com.analysis.graph.web.library.service;

import com.analysis.graph.common.util.LoggerUtils;
import com.analysis.graph.datasource.DataAggregator;
import com.analysis.graph.datasource.DataProvider;
import com.analysis.graph.datasource.DataSourceSystem;
import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.datasource.aggregation.AggregationView;
import com.mchange.lang.ThrowableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Map;

/**
 * Created by cwc on 2017/4/29 0029.
 */
@Service
public class DataAggregateService {
    private static final Logger logger = LoggerFactory.getLogger(DataAggregateService.class);

    public AggregationResult aggregate(Integer clientId, URI uri, Map<String, String> query, AggregationView aggregationView) {
        DataSourceSystem dataSourceSystem = DataSourceSystem.get(clientId, uri);
        try (DataProvider dataProvider = dataSourceSystem.getDataProvider(query)) {
            DataAggregator aggregator = dataSourceSystem.getDataAggregator(dataProvider);
            aggregator.aggregate(aggregationView);
            return aggregator.getAggregateResult();
        } catch (Exception e) {
            LoggerUtils.builder(logger)
                    .format("aggregate data failed, due to {}")
                    .addParam(e.getMessage())
                    .addExtra(ThrowableUtils.extractStackTrace(e))
                    .warn();
            throw new IllegalArgumentException("aggregate data failed", e);
        }
    }

    public String aggregationSQL(Integer clientId, URI uri, Map<String, String> query, AggregationView aggregationView) {
        DataSourceSystem dataSourceSystem = DataSourceSystem.get(clientId, uri);
        try (DataProvider dataProvider = dataSourceSystem.getDataProvider(query)) {
            DataAggregator aggregator = dataSourceSystem.getDataAggregator(dataProvider);
            return aggregator.getAggregationSql(aggregationView);
        } catch (Exception e) {
            LoggerUtils.builder(logger)
                    .format("get aggregation sql failed, due to {}")
                    .addParam(e.getMessage())
                    .addExtra(ThrowableUtils.extractStackTrace(e))
                    .warn();
            throw new IllegalArgumentException("get aggregation sql failed", e);
        }
    }
}
