package com.analysis.graph.web.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.analysis.graph.common.domain.dbo.DataSourceInfo;
import com.analysis.graph.config.DataConfig;
import com.analysis.graph.web.library.repository.DataSourceRepository;
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
@WebMvcTest(DataSourceAPI.class)
@ContextConfiguration(classes = DataConfig.class)
public class DataSourceAPITest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataSourceRepository dataSourceRepository;


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

        DataSourceInfo savedDataSourceInfo = new DataSourceInfo();
        savedDataSourceInfo.setClientId(1);
        savedDataSourceInfo.setName("chinaregion");
//        savedDataSourceInfo.setType("jdbc");
//        savedDataSourceInfo.setConfig(config.toJSONString());
        savedDataSourceInfo.setId(1);
        savedDataSourceInfo.setCreatedTime(new Date());
        savedDataSourceInfo.setUpdatedTime(new Date());

        given(dataSourceRepository.insertDataSource(anyObject())).willReturn(savedDataSourceInfo);

        this.mockMvc.perform(put("/api/data-source")
                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .param("type", savedDataSourceInfo.getType())
//                .param("config", savedDataSourceInfo.getConfig())
                .param("name", savedDataSourceInfo.getName()))
                .andReturn();

        verify(dataSourceRepository, atLeastOnce()).insertDataSource(savedDataSourceInfo);

    }

    @Test
    public void updateDataSource() throws Exception {

    }

    @Test
    public void deleteDataSourceInfo() throws Exception {

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