package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.Graph;
import com.analysis.graph.config.DataConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by cwc on 2017/5/7 0007.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class})
@Transactional
public class GraphRepositoryTest {
    @Resource
    private GraphRepository graphRepository;

    private Graph graph() {
        Graph graph = new Graph();
        graph.setName("graph");
        graph.setCategory("图表");
        graph.setClientId(1);
        graph.setDatasetId(1L);
        graph.setGraphType("table");
        return graph;
    }

    @Test
    public void insertGraph() throws Exception {
    }

    @Test
    public void updateGraph() throws Exception {

    }

    @Test
    public void listGraph() throws Exception {

    }

    @Test
    public void deleteGraph() throws Exception {

    }

    @Test
    public void queryGraph() throws Exception {

    }

}