package com.analysis.graph.web.api.controller;

import com.alibaba.fastjson.JSON;
import com.analysis.graph.common.domain.dbo.Dataset;
import com.analysis.graph.common.domain.dbo.Datasource;
import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.datasource.aggregation.AggregationQuery;
import com.analysis.graph.web.library.repository.DatasetRepository;
import com.analysis.graph.web.library.repository.DatasourceRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import com.analysis.graph.web.library.service.DataAggregateService;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Map;

/**
 * Created by cwc on 2017/4/29 0029.
 */
@RestController
@RequestMapping("/api/aggregate")
@SuppressWarnings("Duplicates")
public class DataAggregateAPI {

    @Resource
    private DataAggregateService dataAggregateService;

    @Resource
    private DatasetRepository dataSetRepository;

    @Resource
    private DatasourceRepository dataSourceRepository;

    @Resource
    private SessionRepository sessionRepository;

    @RequestMapping(value = "/datasource/result", method = RequestMethod.GET)
    public AggregationResult aggregateDataFromDatasource(@RequestParam Integer dataSourceId, @RequestParam String query, @RequestParam AggregationQuery view) {
        Datasource datasource = dataSourceRepository.queryDatasourceById(dataSourceId);
        Map<String, String> queryParam = Maps.transformValues(JSON.parseObject(query), Functions.toStringFunction());
        return dataAggregateService.aggregate(sessionRepository.getUserId(), URI.create(datasource.getUri()), queryParam, view);
    }

    @RequestMapping(value = "/datasource/sql", method = RequestMethod.GET)
    public String previewAggregateSQLFromDatasource(@RequestParam Integer dataSourceId, @RequestParam String query, @RequestParam AggregationQuery view) {
        Datasource dataSourceInfo = dataSourceRepository.queryDatasourceById(dataSourceId);

        Map<String, String> queryParam = Maps.transformValues(JSON.parseObject(query), Functions.toStringFunction());

        return dataAggregateService.aggregationSQL(sessionRepository.getUserId(), URI.create(dataSourceInfo.getUri()), queryParam, view);
    }

    @RequestMapping(value = "/dataset/result", method = RequestMethod.GET)
    public AggregationResult aggregateDataFromDataset(@RequestParam Long dataSetId, @RequestParam AggregationQuery view) {
        Dataset dataSet = dataSetRepository.queryDataset(dataSetId);
        Datasource datasource = dataSourceRepository.queryDatasourceById(dataSet.getDatasourceId());
        Map<String, String> queryParam = Maps.transformValues(JSON.parseObject(dataSet.getQuery()), Functions.toStringFunction());
        return dataAggregateService.aggregate(sessionRepository.getUserId(), URI.create(datasource.getUri()), queryParam, view);
    }

    @RequestMapping(value = "/dataset/sql", method = RequestMethod.GET)
    public String previewAggregateSQLFromDataset(@RequestParam Long dataSetId, @RequestParam AggregationQuery view) {
        Dataset dataSet = dataSetRepository.queryDataset(dataSetId);
        Datasource dataSourceInfo = dataSourceRepository.queryDatasourceById(dataSet.getDatasourceId());
        Map<String, String> queryParam = Maps.transformValues(JSON.parseObject(dataSet.getQuery()), Functions.toStringFunction());
        return dataAggregateService.aggregationSQL(sessionRepository.getUserId(), URI.create(dataSourceInfo.getUri()), queryParam, view);
    }

}
