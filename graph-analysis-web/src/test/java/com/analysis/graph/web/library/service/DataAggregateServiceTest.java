package com.analysis.graph.web.library.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.analysis.graph.config.DataConfig;
import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.datasource.aggregation.AggregationView;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cwc on 2017/4/30 0030.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DataConfig.class)
public class DataAggregateServiceTest {
    @Autowired
    private DataAggregateService dataAggregateService;

    @Test
    public void aggregationSQL() throws Exception {
        Integer clientId = 1;
        URI uri = URI.create("jdbc://root:123@127.0.0.1:3306/chinaregion?db=mysql&pooled=false&aggregatable=true");
        Map<String, String> query = new HashMap<>();
        query.put("sql", "SELECT\n" +
                "c.city_id,\n" +
                "c.city_name,\n" +
                "p.province_id,\n" +
                "p.province_name\n" +
                "FROM\n" +
                "city c\n" +
                "INNER JOIN province p ON c.province_id = p.province_id");

        AggregationView view = new AggregationView();
        AggregationView.DimensionView rows_city_id = new AggregationView.DimensionView();
        rows_city_id.setName("city_id");
        rows_city_id.setFilter("=");
        rows_city_id.setValues(Arrays.asList("130600", "130400", "130500", "130700", "130800", "130900", "131000", "131100", "140100"));

        AggregationView.DimensionView rows_city_name = new AggregationView.DimensionView();
        rows_city_name.setName("city_name");
        rows_city_name.setFilter("=");
        rows_city_name.setValues(Arrays.asList());

        AggregationView.DimensionView rows_province_name = new AggregationView.DimensionView();
        rows_province_name.setName("province_name");
        rows_province_name.setFilter("=");
        rows_province_name.setValues(Arrays.asList());

        view.setRows(Arrays.asList(rows_city_id, rows_city_name, rows_province_name));

        view.setColumns(Collections.emptyList());

        AggregationView.DimensionView filters = new AggregationView.DimensionView();
        rows_province_name.setName("province_name");
        rows_province_name.setFilter("≠");
        rows_province_name.setValues(Arrays.asList("北京市"));
        view.setFilters(Arrays.asList(filters));

        AggregationView.ValueView values = new AggregationView.ValueView();
        values.setColumn("city_name");
        values.setMethod("count");
        view.setValues(Arrays.asList(values));

        System.out.println(JSON.toJSON(view));


        String sql = dataAggregateService.aggregationSQL(clientId, uri, query, view);
        Assert.assertNotNull(sql);
    }

    @Test
    public void aggregationSQL2() throws IOException {
        Integer clientId = 1;
        URI uri = URI.create("jdbc://root:123@127.0.0.1:3306/chinaregion?db=mysql&pooled=false&aggregatable=true");
        Map<String, String> query = new HashMap<>();
        query.put("sql", "SELECT\n" +
                "c.city_id,\n" +
                "c.city_name,\n" +
                "p.province_id,\n" +
                "p.province_name\n" +
                "FROM\n" +
                "city c\n" +
                "INNER JOIN province p ON c.province_id = p.province_id");

        String aggregationViewExample = "{'columns':[],'values':[{'method':'COUNT','column':'city_name'}],'filters':[{}],'rows':[{'filter':'EQUAL','values':['130600','130400','130500','130700','130800','130900','131000','131100','140100'],'name':'city_id'},{'filter':'EQUAL','values':[],'name':'city_name'},{'filter':'NOT_EQUAL','values':['北京市'],'name':'province_name'}]}";
        AggregationView view = JSON.toJavaObject(JSONObject.parseObject(aggregationViewExample), AggregationView.class);

        String aggreagtionSql = dataAggregateService.aggregationSQL(clientId, uri, query, view);
        Assert.assertNotNull(aggreagtionSql);
    }

    @Test
    public void aggregate() throws Exception {
        Integer clientId = 1;
        URI uri = URI.create("jdbc://root:123@127.0.0.1:3306/chinaregion?db=mysql&pooled=false&aggregatable=true");
        Map<String, String> query = new HashMap<>();
        query.put("sql", "SELECT\n" +
                "c.city_id,\n" +
                "c.city_name,\n" +
                "p.province_id,\n" +
                "p.province_name\n" +
                "FROM\n" +
                "city c\n" +
                "INNER JOIN province p ON c.province_id = p.province_id");

        String aggregationViewExample = "{'columns':[],'values':[{'method':'COUNT','column':'city_name'}],'filters':[{}],'rows':[{'filter':'EQUAL','values':['130600','130400','130500','130700','130800','130900','131000','131100','140100'],'name':'city_id'},{'filter':'EQUAL','values':[],'name':'city_name'},{'filter':'NOT_EQUAL','values':['北京市'],'name':'province_name'}]}";
        AggregationView view = JSON.toJavaObject(JSONObject.parseObject(aggregationViewExample), AggregationView.class);

        AggregationResult result = dataAggregateService.aggregate(clientId, uri, query, view);
        Assert.assertNotNull(result);
        System.out.println(JSON.toJSONString(result));
    }

}