package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.constant.GlobalConstant;
import com.analysis.graph.common.domain.dbo.Widget;
import com.analysis.graph.common.domain.dbo.WidgetExample;
import com.analysis.graph.common.repository.mapper.WidgetMapper;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cwc on 2017/5/4 0004.
 */
@Repository
public class WidgetRepository {

    @Resource
    private WidgetMapper graphMapper;

    @Transactional
    public Widget saveWidget(Widget graph) {
        sanitize(graph);
        DateTime now = new DateTime();
        //graph.setCreatedTime(now.toDate());
       // graph.setUpdatedTime(now.toDate());
        graphMapper.insert(graph);
        return graph;
    }

    private Widget sanitize(Widget graph) {
        if (StringUtils.isEmpty(graph.getCategory())) {
            graph.setCategory(GlobalConstant.UN_CATEGORY);
        }
        return graph;

    }

    @Transactional
    public Widget updateWidget(Widget graph) {
        sanitize(graph);
        graph.setUpdatedTime(new DateTime().toDate());
        if (graphMapper.updateByPrimaryKey(graph) != 1) {
            throw new RuntimeException();
        }
        return graph;
    }

    public List<Widget> listWidgetForClient(Integer clientId) {
        WidgetExample example = new WidgetExample();
        example.createCriteria().andClientIdEqualTo(clientId);
        return graphMapper.selectByExample(example);
    }

    @Transactional
    public void removeWidget(Long graphId) {
        graphMapper.deleteByPrimaryKey(graphId);
    }


    public Widget getWidget(Long id) {
        Widget graph = graphMapper.selectByPrimaryKey(id);
        if (graph == null) {
            throw new IllegalArgumentException("can not find client's graph by id：" + id);
        }
        return graph;
    }
}
