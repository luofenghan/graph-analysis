package com.analysis.graph.web.api.controller;

import com.alibaba.fastjson.JSON;
import com.analysis.graph.common.domain.dbo.DataSet;
import com.analysis.graph.common.domain.dbo.DataSourceInfo;
import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.datasource.aggregation.AggregationView;
import com.analysis.graph.web.library.repository.DataSetRepository;
import com.analysis.graph.web.library.repository.DataSourceRepository;
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
@RequestMapping("/api/aggregation")
@SuppressWarnings("Duplicates")
public class DataAggregateAPI {

    @Resource
    private DataAggregateService dataAggregateService;

    @Resource
    private DataSetRepository dataSetRepository;

    @Resource
    private DataSourceRepository dataSourceRepository;

    @Resource
    private SessionRepository sessionRepository;

    @RequestMapping(value = "/{datasource}/result", method = RequestMethod.GET)
    public AggregationResult aggregateData(@PathVariable String datasource,
                                           @RequestParam(required = false) Long dataSetId,
                                           @RequestParam(required = false) Integer dataSourceId, @RequestParam(required = false) String query,
                                           @RequestParam AggregationView view) {
        DataSourceInfo dataSourceInfo;
        switch (datasource) {
            case "new-data-source":
                dataSourceInfo = dataSourceRepository.queryDataSourceInfoById(dataSourceId);
                break;
            case "data-set":
                DataSet dataSet = dataSetRepository.queryDataSet(dataSetId);
                dataSourceInfo = dataSourceRepository.queryDataSourceInfoById(dataSet.getDataSourceId());
                query = dataSet.getQuery();
                break;
            default:
                throw new IllegalArgumentException(String.format("datasource:%s didn't match", datasource));
        }
        Map<String, String> queryParam = Maps.transformValues(JSON.parseObject(query), Functions.toStringFunction());
        return dataAggregateService.aggregate(sessionRepository.getUserId(), URI.create(dataSourceInfo.getUri()), queryParam, view);
    }

    @RequestMapping(value = "/{datasource}/sql/preview", method = RequestMethod.GET)
    public String previewSqlOfAggregation(@PathVariable String datasource,
                                          @RequestParam(required = false) Long dataSetId,
                                          @RequestParam(required = false) Integer dataSourceId, @RequestParam(required = false) String query,
                                          @RequestParam AggregationView view) {

        DataSourceInfo dataSourceInfo;
        switch (datasource) {
            case "new-data-source":
                dataSourceInfo = dataSourceRepository.queryDataSourceInfoById(dataSourceId);
                break;
            case "data-set":
                DataSet dataSet = dataSetRepository.queryDataSet(dataSetId);
                query = dataSet.getQuery();
                dataSourceInfo = dataSourceRepository.queryDataSourceInfoById(dataSet.getDataSourceId());
                break;
            default:
                throw new IllegalArgumentException(String.format("datasource:%s didn't match", datasource));
        }
        Map<String, String> queryParam = Maps.transformValues(JSON.parseObject(query), Functions.toStringFunction());
        return dataAggregateService.aggregationSQL(sessionRepository.getUserId(), URI.create(dataSourceInfo.getUri()), queryParam, view);
    }

}
