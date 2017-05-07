package com.analysis.graph.web.library.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.analysis.graph.common.domain.dbo.Dataset;
import com.analysis.graph.common.domain.dbo.Datasource;
import com.analysis.graph.common.util.LoggerUtils;
import com.analysis.graph.datasource.DataProvider;
import com.analysis.graph.datasource.DataSourceSystem;
import com.analysis.graph.datasource.aggregation.*;
import com.analysis.graph.web.library.repository.DatasetRepository;
import com.analysis.graph.web.library.repository.DatasourceRepository;
import com.analysis.graph.web.library.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import com.mchange.lang.ThrowableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Created by cwc on 2017/4/29 0029.
 */
@Service
public class AggregationService {
    @Resource
    private DatasetRepository dataSetRepository;

    @Resource
    private DatasourceRepository dataSourceRepository;

    private static final Logger logger = LoggerFactory.getLogger(AggregationService.class);

    @SuppressWarnings("unchecked")
    public AggregationResult aggregate(Integer clientId, Dataset dataset) {
        DimensionView aggregationQuery = new DimensionView();
        if (dataset.getFilter() != null) {
            JSONArray.parseArray(dataset.getFilter());
            List<Field> filters = JsonUtils.toJavaObject(dataset.getFilter(), new TypeReference<List<Field>>() {
            });
            aggregationQuery.setFilters(filters);
        }
        Datasource dataSourceInfo = dataSourceRepository.getDatasource(dataset.getDatasourceId());
        return aggregate(clientId, dataSourceInfo.getUri(), dataset.getQuery(), aggregationQuery);
    }

    public AggregationResult aggregate(Integer clientId, Long datasetId, DimensionView aggregationView) {
        Dataset dataSet = dataSetRepository.getDataset(datasetId);
        Datasource dataSourceInfo = dataSourceRepository.getDatasource(dataSet.getDatasourceId());
        return aggregate(clientId, dataSourceInfo.getUri(), dataSet.getQuery(), aggregationView);
    }

    private AggregationResult aggregate(Integer clientId, String uri, String queryStr, DimensionView aggregationView) {
        Map<String, String> queryParam = Maps.transformValues(JsonUtils.toJsonObject(queryStr), Functions.toStringFunction());

        DataSourceSystem dataSourceSystem = DataSourceSystem.get(clientId, URI.create(uri));
        try (DataProvider dataProvider = dataSourceSystem.getDataProvider(queryParam)) {
            Aggregator aggregator = dataSourceSystem.getDataAggregator(dataProvider);
            return aggregator.doAggregation(aggregationView);
        } catch (Exception e) {
            LoggerUtils.builder(logger)
                    .format("aggregate data failed, due to {}")
                    .addParam(e.getMessage())
                    .addExtra(ThrowableUtils.extractStackTrace(e))
                    .warn();
            throw new IllegalArgumentException("aggregate data failed", e);
        }
    }

    public String getAggregationSQL(Integer clientId, URI uri, String queryStr, DimensionView aggregationView) {
        Map<String, String> queryParam = Maps.transformValues(JSON.parseObject(queryStr), Functions.toStringFunction());
        DataSourceSystem dataSourceSystem = DataSourceSystem.get(clientId, uri);
        try (DataProvider dataProvider = dataSourceSystem.getDataProvider(queryParam)) {
            Aggregator aggregator = dataSourceSystem.getDataAggregator(dataProvider);
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
