package com.analysis.graph.web.library.repository;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.analysis.graph.common.domain.dbo.Dataset;
import com.analysis.graph.config.DataConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by cwc on 2017/5/6 0006.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class})
@Transactional
public class DatasetRepositoryTest {
    @Autowired
    private DatasetRepository datasetRepository;

    private Dataset dataset() {
        Dataset dataset = new Dataset();
        dataset.setId(null);
        dataset.setCategory("数据集分类");
        dataset.setName("数据集名称");
        dataset.setClientId(1);
        dataset.setDatasourceId(1);

        /*表达式*/
        JSONArray expressions = new JSONArray();
        JSONObject exp1 = new JSONObject();
        exp1.put("exp", "count(city_name)");
        exp1.put("alias", "CountCityOfProvince");
        expressions.add(exp1);

        dataset.setExpression(expressions.toJSONString());

        /*过滤器*/
        JSONArray filters = new JSONArray();
        JSONObject filter1 = new JSONObject();
        filter1.put("label", "created_time");
        JSONArray values = new JSONArray();
        values.add("now('W',-1,'yyyy-MM-dd')");
        filter1.put("values", values.toJSONString());
        filter1.put("type", ">");
        filter1.put("name", "创建时间不为1");
        filters.add(filter1);
        dataset.setFilter(filters.toJSONString());


        JSONObject query = new JSONObject();
        query.put("sql", "select * from dual");
        dataset.setQuery(query.toJSONString());
        return dataset;
    }

    @Test
    @Rollback
    public void insertDataset() throws Exception {
        Dataset dataset = dataset();
        dataset = datasetRepository.insertDataset(dataset);
        Assert.assertNotNull(dataset.getId());
    }

    @Test
    @Rollback
    public void updateDataset() throws Exception {
        Dataset dataset = dataset();
        dataset = datasetRepository.insertDataset(dataset);
        Assert.assertNotNull(dataset.getId());

        Dataset dataset2 = dataset();
        dataset2.setId(dataset.getId());
        dataset2.setQuery("select * from dual");
        datasetRepository.updateDataset(dataset2);

        Dataset queriedDataSet = datasetRepository.queryDataset(dataset2.getId());
        System.out.println(JSON.toJSONString(queriedDataSet));
        Assert.assertNotNull(queriedDataSet.getName());

    }

    @Test(expected = IllegalArgumentException.class)
    @Rollback
    public void deleteDataset() throws Exception {
        Dataset dataset = dataset();
        dataset = datasetRepository.insertDataset(dataset);
        Assert.assertNotNull(dataset.getId());

        datasetRepository.deleteDataset(dataset.getId());

        datasetRepository.queryDataset(dataset.getId());
    }

    @Test
    @Rollback
    public void queryClientDataset() throws Exception {
        Dataset dataset = dataset();
        dataset = datasetRepository.insertDataset(dataset);
        Assert.assertNotNull(dataset.getId());

        List<Dataset> clientDataSets = datasetRepository.queryClientDataset(dataset.getClientId());
        Assert.assertTrue(clientDataSets.size() == 1);
    }

}