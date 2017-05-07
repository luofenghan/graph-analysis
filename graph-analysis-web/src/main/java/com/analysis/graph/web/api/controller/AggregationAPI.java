package com.analysis.graph.web.api.controller;

import com.analysis.graph.common.domain.dbo.Dataset;
import com.analysis.graph.common.domain.dbo.Datasource;
import com.analysis.graph.datasource.aggregation.AggregationView;
import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.web.library.repository.DatasetRepository;
import com.analysis.graph.web.library.repository.DatasourceRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import com.analysis.graph.web.library.service.AggregationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URI;

/**
 * Created by cwc on 2017/4/29 0029.
 */
@RestController
@RequestMapping("/api/aggregation")
@SuppressWarnings("Duplicates")
public class AggregationAPI {

    @Resource
    private AggregationService dataAggregateService;

    @Resource
    private DatasetRepository dataSetRepository;

    @Resource
    private DatasourceRepository dataSourceRepository;

    @Resource
    private SessionRepository sessionRepository;

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public AggregationResult aggregateDataFromDataset(@RequestParam Long dataSetId, @RequestParam AggregationView view) {
        return dataAggregateService.aggregate(sessionRepository.getUserId(), dataSetId, view);
    }

    @RequestMapping(value = "/sql", method = RequestMethod.GET)
    public String previewAggregateSQLFromDataset(@RequestParam Long dataSetId, @RequestParam AggregationView view) {
        Dataset dataSet = dataSetRepository.queryDataset(dataSetId);
        Datasource dataSourceInfo = dataSourceRepository.queryDatasourceById(dataSet.getDatasourceId());
        return dataAggregateService.getAggregationSQL(sessionRepository.getUserId(), URI.create(dataSourceInfo.getUri()), dataSet.getQuery(), view);
    }
}
