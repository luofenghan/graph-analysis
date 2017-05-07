package com.analysis.graph.web.api.controller;

import com.analysis.graph.common.domain.dbo.Dataset;
import com.analysis.graph.common.domain.dbo.Datasource;
import com.analysis.graph.common.domain.dbo.Widget;
import com.analysis.graph.config.DataConfig;
import com.analysis.graph.datasource.aggregation.*;
import com.analysis.graph.web.library.repository.DatasetRepository;
import com.analysis.graph.web.library.repository.DatasourceRepository;
import com.analysis.graph.web.library.repository.WidgetRepositoryTest;
import com.analysis.graph.web.library.service.AggregationService;
import com.analysis.graph.web.library.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cwc on 2017/5/7 0007.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class})
public class AggregationAPITest {
    @Resource
    private DatasetRepository datasetRepository;

    @Resource
    private WidgetRepositoryTest graphRepositoryTest;

    @Resource
    private DatasourceRepository datasourceRepository;

    @Resource
    private AggregationService aggregationService;

    @Test
    public void aggregateDataFromDataset() throws Exception {
        Datasource datasource = new Datasource();
        datasource.setClientId(1);
        datasource.setName("数据源名称");
        //jdbc:h2:mem:test
        datasource.setUri("jdbc://sa@mem/test?db=h2&aggregatable=true&pooled=false");
        datasource.setId(null);
        datasource = datasourceRepository.saveDatasource(datasource);

        Dataset dataset = new Dataset();
        dataset.setName("全球市场数据集");
        dataset.setClientId(1);

        Filter filter = new Filter();
        filter.setType(Filter.Type.GREATER_THAN);
        filter.setValues(Collections.singletonList("100"));
        Field field = new Field();
        field.setName("profit");
        field.setAlias("利润大于100");
        field.setFilter(filter);
        dataset.setFilter(JsonUtils.toJsonString(field));

        Map<String, String> query = new HashMap<>();
        query.put("sql", "SELECT o.* FROM orders o INNER JOIN returns r ON o.order_id = r.order_id");
        dataset.setQuery(JsonUtils.toJsonString(query));

        dataset.setDatasourceId(datasource.getId());

        dataset = datasetRepository.saveDataset(dataset);

        Widget graph = graphRepositoryTest.graph();

        DimensionView dimensionView = new DimensionView();
        dimensionView.setColumns(JsonUtils.toJavaObject(graph.getColumnField(), new TypeReference<List<Field>>() {
        }));
        dimensionView.setRows(JsonUtils.toJavaObject(graph.getRowField(), new TypeReference<List<Field>>() {
        }));
        dimensionView.setFilters(JsonUtils.toJavaObject(graph.getFilterField(), new TypeReference<List<Field>>() {
        }));
        dimensionView.setMetrics(JsonUtils.toJavaObject(graph.getMetricField(), new TypeReference<List<Metric>>() {
        }));


        AggregationResult aggregationResult = aggregationService.aggregate(1, dataset.getId(), dimensionView);

        Assert.assertNotNull(aggregationResult);
    }

    @Test
    public void previewAggregateSQLFromDataset() throws Exception {

    }

}