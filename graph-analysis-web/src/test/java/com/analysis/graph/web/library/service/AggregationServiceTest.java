package com.analysis.graph.web.library.service;

import com.alibaba.fastjson.JSONArray;
import com.analysis.graph.common.domain.dbo.Dataset;
import com.analysis.graph.common.domain.dbo.Datasource;
import com.analysis.graph.config.DataConfig;
import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.datasource.aggregation.ColumnIndex;
import com.analysis.graph.datasource.aggregation.Dimension;
import com.analysis.graph.datasource.aggregation.Measure;
import com.analysis.graph.web.library.repository.DatasourceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * Created by cwc on 2017/5/6 0006.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class})
public class AggregationServiceTest {
    @Resource
    private AggregationService aggregationService;

    @Resource
    private DatasourceRepository datasourceRepository;


    @Test
    public void aggregate() throws Exception {
        /*新建datasource*/
        Datasource datasource = new Datasource();
        datasource.setClientId(1);
        datasource.setName("数据源名称");
        datasource.setUri("jdbc://root:123@localhost:3306/chinaregion?db=mysql&aggregatable=true&pooled=true");
        datasource.setId(null);
        datasource = datasourceRepository.insertDataSource(datasource);

        /*初始化数据集*/
        Dataset dataset = new Dataset();
        dataset.setQuery("{\"sql\":\"SELECT * FROM city \"}");
        dataset.setName("name");
        dataset.setCategory("分类");
        dataset.setClientId(1);
        dataset.setDatasourceId(datasource.getId());

        Dimension dimension = new Dimension();
        dimension.setFilterType(Dimension.FilterType.EQUAL.getSymbol());
        dimension.setName("city_id");
        dimension.setValues(Arrays.asList("130600", "130400", "130500", "130700", "130800", "130900", "131000"));
        JSONArray filters = new JSONArray();
        filters.add(dimension);
        dataset.setFilter(filters.toJSONString().replace("EQUAL", "="));

        Measure measure = new Measure();
        measure.setColumn("city_name");
        measure.setFunction(Measure.Function.COUNT);
        JSONArray measures = new JSONArray();
        measures.add(measure);
       // dataset.setExpression(measures.toJSONString().replace("COUNT", "count"));
        dataset.setExpression(null);
        AggregationResult aggregationResult = aggregationService.aggregate(1, dataset);

        for (ColumnIndex index : aggregationResult.getColumns()) {
            System.out.println(index.toString());
        }

    }


    @Test
    public void getAggregationSQL() throws Exception {

    }

}