package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.Graph;
import com.analysis.graph.common.domain.dbo.GraphExample;
import com.analysis.graph.common.repository.mapper.GraphMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Created by cwc on 2017/5/4 0004.
 */
@Repository
public class GraphRepository {

    @Resource
    private GraphMapper graphMapper;

    @Transactional
    public Graph insertGraph(Graph graph) {
        checkGraph(graph);
        DateTime now = new DateTime();
        //graph.setCreatedTime(now.toDate());
       // graph.setUpdatedTime(now.toDate());
        graphMapper.insert(graph);
        return graph;
    }

    private Graph checkGraph(Graph graph) {
        if (StringUtils.isEmpty(graph.getCategory())) {
            graph.setCategory("未分类");
        }
        return graph;

    }

    @Transactional
    public Graph updateGraph(Graph graph) {
        checkGraph(graph);
        graph.setUpdatedTime(new DateTime().toDate());
        if (graphMapper.updateByPrimaryKey(graph) != 1) {
            throw new RuntimeException();
        }
        return graph;
    }

    public List<Graph> listGraph(Integer clientId) {
        GraphExample example = new GraphExample();
        example.createCriteria().andClientIdEqualTo(clientId);
        return graphMapper.selectByExample(example);
    }

    @Transactional
    public void deleteGraph(Long graphId) {
        graphMapper.deleteByPrimaryKey(graphId);
    }


    public Graph queryGraph(Long id) {
        Graph graph = graphMapper.selectByPrimaryKey(id);
        if (graph == null) {
            throw new IllegalArgumentException("can not find client's graph by id：" + id);
        }
        return graph;
    }
}
