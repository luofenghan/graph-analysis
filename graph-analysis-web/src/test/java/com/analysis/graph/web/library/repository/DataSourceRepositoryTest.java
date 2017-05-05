package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.Datasource;
import com.analysis.graph.config.DataConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by cwc on 2017/4/22 0022.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataConfig.class})
public class DataSourceRepositoryTest {
    @Autowired
    private DatasourceRepository dataSourceRepository;

    @Test
    public void queryDataSourceListByClientId() throws Exception {
        List<Datasource> dataSourceInfos = dataSourceRepository.queryDataSourceListByClientId(1);
        Assert.assertNotNull(dataSourceInfos);
    }

}