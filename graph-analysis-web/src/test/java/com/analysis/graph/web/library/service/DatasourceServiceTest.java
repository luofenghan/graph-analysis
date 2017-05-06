package com.analysis.graph.web.library.service;

import com.analysis.graph.config.DataConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cwc on 2017/5/6 0006.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class})
public class DatasourceServiceTest {
    @Resource
    private DatasourceService datasourceService;

    @Test
    public void getDataSourceSupportList() throws Exception {
        List<String> list = datasourceService.getDataSourceSupportList();
        Assert.assertTrue(!list.isEmpty());
    }

    @Test
    public void getFreshData() throws Exception {
        URI uri = URI.create("jdbc://root:123@localhost:3306/chinaregion?db=mysql&aggregatable=true&pooled=true");
        Integer clientId = 1;
        Map<String, String> query = new HashMap<>();
        query.put("sql", "select * from city");
        Object[][] objects = datasourceService.getFreshData(clientId, uri, query);
        Assert.assertTrue(objects.length != 0);

    }

}