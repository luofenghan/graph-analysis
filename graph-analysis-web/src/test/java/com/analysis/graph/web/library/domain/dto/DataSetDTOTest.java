package com.analysis.graph.web.library.domain.dto;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cwc on 2017/4/23 0023.
 */
public class DataSetDTOTest {

    @Test
    public void testDataSetDTO() {
        DataSetDTO dataSetDTO = new DataSetDTO();
        dataSetDTO.setId(2L);
        dataSetDTO.setClientId(1);
        dataSetDTO.setCategoryName("北京");
        dataSetDTO.setDataSetName("所有市辖区");
        dataSetDTO.setDataSourceId(2);
        dataSetDTO.setInterval(13L);

        Map<String, String> query = new HashMap<>();
        query.put("sql", "select * from city");
        query.put("saiku", "select * from city2");
        dataSetDTO.setQuery(query);

        /*设置表达式*/
        List<DataSetDTO.Expression> expressions = new ArrayList<>();
        DataSetDTO.Expression expression1 = new DataSetDTO.Expression();
        expression1.setAlias("CountCityOfProvince");
        expression1.setType("exp");
        expression1.setExp("count(city_name)");
        expressions.add(expression1);

        DataSetDTO.Expression expression2 = new DataSetDTO.Expression();
        expression2.setAlias("CountCityOfProvince");
        expression2.setType("exp");
        expression2.setExp("count(city_name)");
        expressions.add(expression2);
        dataSetDTO.setExpressions(expressions.toArray(new DataSetDTO.Expression[2]));


        /*设置过滤器组*/
        DataSetDTO.FilterGroup[] filterGroups = new DataSetDTO.FilterGroup[2];

        DataSetDTO.FilterGroup group1 = new DataSetDTO.FilterGroup();
        group1.setName("创建时间大于一周");
        DataSetDTO.FilterGroup.Filter group1_filter1 = new DataSetDTO.FilterGroup.Filter();
        group1_filter1.setColName("created_time");
        group1_filter1.setValues(new String[]{"{now('W',-1,'yyyy-MM-dd')}"});
        group1_filter1.setType(">");
        group1.setFilters(new DataSetDTO.FilterGroup.Filter[]{group1_filter1});

        DataSetDTO.FilterGroup group2 = new DataSetDTO.FilterGroup();
        group2.setName("province不为江苏");
        DataSetDTO.FilterGroup.Filter group2_filter1 = new DataSetDTO.FilterGroup.Filter();
        group2_filter1.setColName("province_id");
        group2_filter1.setValues(new String[]{"12000"});
        group2_filter1.setType("≠");
        group2.setFilters(new DataSetDTO.FilterGroup.Filter[]{group2_filter1});

        filterGroups[0] = group1;
        filterGroups[1] = group2;

        dataSetDTO.setFilterGroups(filterGroups);


        String jsonResult = JSON.toJSONString(dataSetDTO);
        Assert.assertNotNull(jsonResult);

        DataSetDTO newDataSet = JSON.parseObject(jsonResult, DataSetDTO.class);

        Assert.assertEquals(newDataSet.getDataSetName(), dataSetDTO.getDataSetName());
    }


}