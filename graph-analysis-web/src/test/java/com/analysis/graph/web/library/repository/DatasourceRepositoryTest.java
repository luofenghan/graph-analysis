package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.Datasource;
import com.analysis.graph.config.DataConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cwc on 2017/5/6 0006.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class})
@Transactional
public class DatasourceRepositoryTest {
    @Resource
    private DatasourceRepository datasourceRepository;


    private Datasource datasource() {
        Datasource datasource = new Datasource();
        datasource.setClientId(1);
        datasource.setName("数据源名称");
        datasource.setUri("jdbc://root:123@localhost:3306/chinaregion?db=mysql&aggregatiable=true&pooled=true");
        datasource.setId(null);
        return datasource;
    }

    @Test
    @Rollback
    public void queryDatasourceById() throws Exception {
        Datasource datasource = datasource();
        datasource = datasourceRepository.insertDataSource(datasource);
        Assert.assertNotNull(datasource.getId());

        datasourceRepository.queryDatasourceById(datasource.getId());
    }

    @Test
    @Rollback
    public void queryDataSourceListByClientId() throws Exception {
        Datasource datasource = datasource();
        datasource = datasourceRepository.insertDataSource(datasource);
        Assert.assertNotNull(datasource.getId());

        List<Datasource> datasources = datasourceRepository.queryDataSourceListByClientId(1);
        Assert.assertTrue(datasources.size() == 1);
    }

    @Test
    @Rollback
    public void insertDataSource() throws Exception {
        Datasource datasource = datasource();
        datasource = datasourceRepository.insertDataSource(datasource);
        Assert.assertNotNull(datasource.getId());
    }

    @Test
    @Rollback
    public void updateDataSource() throws Exception {
        Datasource datasource = datasource();
        datasource = datasourceRepository.insertDataSource(datasource);
        Assert.assertNotNull(datasource.getId());

        Datasource datasource1 = new Datasource();
        datasource1.setId(datasource.getId());
        datasource1.setName("new Name");

        datasourceRepository.updateDataSource(datasource1);

        Datasource updated = datasourceRepository.queryDatasourceById(datasource.getId());
        Assert.assertEquals("new Name", updated.getName());
        Assert.assertNotNull(updated.getClientId());
    }

    @Test(expected = IllegalArgumentException.class)
    @Rollback
    public void deleteDataSource() throws Exception {
        Datasource datasource = datasource();
        datasource = datasourceRepository.insertDataSource(datasource);
        Assert.assertNotNull(datasource.getId());

        datasourceRepository.deleteDataSource(datasource.getId());

        datasourceRepository.queryDatasourceById(datasource.getId());
    }

}