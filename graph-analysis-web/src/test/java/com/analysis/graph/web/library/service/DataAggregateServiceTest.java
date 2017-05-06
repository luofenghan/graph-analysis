package com.analysis.graph.web.library.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.analysis.graph.config.DataConfig;
import com.analysis.graph.datasource.aggregation.Measure;
import com.analysis.graph.datasource.aggregation.AggregationView;
import com.analysis.graph.datasource.aggregation.AggregationResult;
import com.analysis.graph.datasource.aggregation.Dimension;
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
    private AggregationService dataAggregateService;

    @Test
    public void aggregationSQL() throws Exception {
        Integer clientId = 1;
        URI uri = URI.create("jdbc://root:123@127.0.0.1:3306/chinaregion?db=mysql&pooled=false&aggregatable=true");
        JSONObject query = new JSONObject();
        query.put("sql", "SELECT\n" +
                "c.city_id,\n" +
                "c.city_name,\n" +
                "p.province_id,\n" +
                "p.province_name\n" +
                "FROM\n" +
                "city c\n" +
                "INNER JOIN province p ON c.province_id = p.province_id");

        AggregationView view = new AggregationView();
        Dimension rows_city_id = new Dimension();
        rows_city_id.setName("city_id");
        rows_city_id.setFilterType("=");
        rows_city_id.setValues(Arrays.asList("130600", "130400", "130500", "130700", "130800", "130900", "131000", "131100", "140100"));

        Dimension rows_city_name = new Dimension();
        rows_city_name.setName("city_name");
        rows_city_name.setFilterType("=");
        rows_city_name.setValues(Arrays.asList());

        Dimension rows_province_name = new Dimension();
        rows_province_name.setName("province_name");
        rows_province_name.setFilterType("=");
        rows_province_name.setValues(Arrays.asList());

        view.setRows(Arrays.asList(rows_city_id, rows_city_name, rows_province_name));

        view.setColumns(Collections.emptyList());

        Dimension filters = new Dimension();
        rows_province_name.setName("province_name");
        rows_province_name.setFilterType("≠");
        rows_province_name.setValues(Arrays.asList("北京市"));
        view.setFilters(Arrays.asList(filters));

        Measure values = new Measure();
        values.setColumn("city_name");
        values.setFunction("count");
        view.setMeasures(Arrays.asList(values));

        System.out.println(JSON.toJSON(view));


        String sql = dataAggregateService.getAggregationSQL(clientId, uri, query.toJSONString(), view);
        Assert.assertNotNull(sql);
    }

    @Test
    public void aggregationSQL2() throws IOException {
        Integer clientId = 1;
        URI uri = URI.create("jdbc://root:123@127.0.0.1:3306/chinaregion?db=mysql&pooled=false&aggregatable=true");
        JSONObject query = new JSONObject();
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

        String aggreagtionSql = dataAggregateService.getAggregationSQL(clientId, uri, query.toJSONString(), view);
        Assert.assertNotNull(aggreagtionSql);
    }

    @Test
    public void aggregate() throws Exception {
        Integer clientId = 1;
        URI uri = URI.create("jdbc://root:123@127.0.0.1:3306/chinaregion?db=mysql&pooled=false&aggregatable=true");
        Map<String, String> query = new HashMap<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sql", "SELECT\n" +
                "c.city_id,\n" +
                "c.city_name,\n" +
                "p.province_id,\n" +
                "p.province_name\n" +
                "FROM\n" +
                "city c\n" +
                "INNER JOIN province p ON c.province_id = p.province_id");

        String aggregationViewExample = "{'columns':[],'values':[{'method':'COUNT','column':'city_name'}],'filters':[{}],'rows':[{'filter':'EQUAL','values':['130600','130400','130500','130700','130800','130900','131000','131100','140100'],'name':'city_id'},{'filter':'EQUAL','values':[],'name':'city_name'},{'filter':'NOT_EQUAL','values':['北京市'],'name':'province_name'}]}";
        AggregationView view = JSON.toJavaObject(JSONObject.parseObject(aggregationViewExample), AggregationView.class);

        AggregationResult result = dataAggregateService.aggregate(clientId, uri.toString(), jsonObject.toJSONString(), view);
        Assert.assertNotNull(result);
        System.out.println(JSON.toJSONString(result));
    }

}