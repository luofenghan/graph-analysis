package com.analysis.graph.web.library.service;

import com.alibaba.fastjson.JSONArray;
import com.analysis.graph.common.domain.dbo.Dataset;
import com.analysis.graph.common.domain.dbo.Datasource;
import com.analysis.graph.config.DataConfig;
import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.datasource.aggregation.Dimension;
import com.analysis.graph.web.library.repository.DatasourceRepository;
import com.analysis.graph.web.library.util.JsonUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.sql.*;
import java.util.Collections;

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
    public void h2Test() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ( SELECT o.* FROM orders o INNER JOIN returns r ON o.order_id = r.order_id LIMIT 1,5000 ) __view__ WHERE 1=0");
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        Assert.assertTrue(resultSetMetaData.getColumnCount() != 0);
    }

    @Test
    public void aggregate() throws Exception {
        /*新建datasource*/
        Datasource datasource = new Datasource();
        datasource.setClientId(1);
        datasource.setName("数据源名称");
        //jdbc:h2:mem:test
        datasource.setUri("jdbc://sa:/test?db=h2&aggregatable=true&pooled=false");
        datasource.setId(null);
        datasource = datasourceRepository.insertDataSource(datasource);

        /*初始化数据集*/
        Dataset dataset = new Dataset();
        dataset.setQuery("{\"sql\":\"SELECT * FROM orders o INNER JOIN returns r ON o.order_id = r.order_id LIMIT 1,5000\"}");
        dataset.setName("name");
        dataset.setCategory("分类");
        dataset.setClientId(1);
        dataset.setDatasourceId(datasource.getId());

        Dimension dimension = new Dimension();
        dimension.setFilter(Dimension.Filter.LESS_THAN);
        dimension.setName("sales");
        dimension.setValues(Collections.singletonList("64.8"));
        JSONArray filters = new JSONArray();
        filters.add(dimension);
        dataset.setFilter(JsonUtils.toJsonString(filters));

        AggregationResult aggregationResult = aggregationService.aggregate(1, dataset);

        for (AggregationResult.ColumnIndex index : aggregationResult.getColumns()) {
            System.out.println(index.toString());
        }

    }


    @Test
    public void getAggregationSQL() throws Exception {

    }

}