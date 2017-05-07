package com.analysis.graph.web.api.controller;

import com.analysis.graph.common.domain.dbo.Graph;
import com.analysis.graph.config.DataConfig;
import com.analysis.graph.web.library.repository.GraphRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by cwc on 2017/5/4 0004.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class})
@WebAppConfiguration
public class GraphAPITest {

    @Mock
    private GraphAPI graphAPI;

    @Mock
    private SessionRepository sessionRepository;
    @Resource
    private GraphRepository graphRepository;

    @Test
    @Rollback
    public void saveGraph() throws Exception {
        Graph unsavedGraph = graph();
        Graph savedGraph = graphAPI.saveGraph(unsavedGraph);
        Assert.assertNotNull(savedGraph.getId());
    }


    private Graph graph() {
        Graph graph = new Graph();
        graph.setCategory("mock_分类");
        graph.setId(null);
        graph.setClientId(1);
        graph.setName("mock_name");
        graph.setRowField("{}");
        graph.setOptionalField("[]");
        graph.setColumnField("{}");
        graph.setMetricField("{}");
        graph.setFilterField("{}");
        graph.setGraphType("table");

        return graph;
    }

    @Test
    @Rollback
    public void updateGraph() throws Exception {
        Graph unsavedGraph = graph();
        Graph savedGraph = graphAPI.saveGraph(unsavedGraph);
        Assert.assertNotNull(savedGraph.getId());

        savedGraph.setRowField("new row");
        Graph updatedGraph = graphAPI.updateGraph(savedGraph);

        Graph queriedGraph = graphRepository.queryGraph(updatedGraph.getId());

        Assert.assertEquals(queriedGraph.getRowField(), updatedGraph.getRowField());

    }

    @Test
    @Rollback
    public void getGraphList() throws Exception {
        when(sessionRepository.getUserId()).thenReturn(1);
        List<Graph> graphs = graphAPI.getGraphList();
        Assert.assertTrue(graphs.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    @Rollback
    public void removeGraph() throws Exception {
        Graph unsavedGraph = graph();
        unsavedGraph.setId(12L);

        Graph savedGraph = graphAPI.saveGraph(unsavedGraph);
        Assert.assertNotNull(savedGraph.getId());

        graphAPI.removeGraph(savedGraph.getId());

        Graph queriedGraph = graphRepository.queryGraph(savedGraph.getId());
        Assert.assertNull(queriedGraph);
    }

    @Test
    @Rollback
    public void getGraphCategoryList() throws Exception {
        when(sessionRepository.getUserId()).thenReturn(1);
        List<String> categoryList = graphAPI.getGraphCategoryList();
        Assert.assertTrue(categoryList.isEmpty());
    }

}