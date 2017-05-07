package com.analysis.graph.web.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.analysis.graph.common.domain.dbo.Datasource;
import com.analysis.graph.config.DataConfig;
import com.analysis.graph.web.library.repository.DatasourceRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

/**
 * Created by cwc on 2017/4/22 0022.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DatasourceAPI.class)
@ContextConfiguration(classes = DataConfig.class)
public class DataSourceAPITest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DatasourceRepository dataSourceRepository;


    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void saveDataSource() throws Exception {
        JSONObject config = new JSONObject();
        config.put("driverClassName", "com.mysql.jdbc.Driver");
        config.put("connectUrl", "jdbc:mysql://localhost:3306/chinaregion");
        config.put("pooled", true);
        config.put("aggregatable", false);
        config.put("username", "root");
        config.put("password", "123");

        Datasource savedDatasource = new Datasource();
        savedDatasource.setClientId(1);
        savedDatasource.setName("chinaregion");
//        savedDatasource.setType("jdbc");
//        savedDatasource.setConfig(config.toJSONString());
        savedDatasource.setId(1);
        savedDatasource.setCreatedTime(new Date());
        savedDatasource.setUpdatedTime(new Date());

        given(dataSourceRepository.saveDatasource(anyObject())).willReturn(savedDatasource);

        this.mockMvc.perform(put("/api/data-source")
                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .param("type", savedDatasource.getType())
//                .param("config", savedDatasource.getConfig())
                .param("name", savedDatasource.getName()))
                .andReturn();

        verify(dataSourceRepository, atLeastOnce()).saveDatasource(savedDatasource);

    }

    @Test
    public void updateDataSource() throws Exception {

    }

    @Test
    public void deleteDatasource() throws Exception {

    }

    @Test
    public void testDataSource() throws Exception {

    }

    @Test
    public void dataSourceList() throws Exception {

    }

    @Test
    public void dataSourceTypeList() throws Exception {

    }

}