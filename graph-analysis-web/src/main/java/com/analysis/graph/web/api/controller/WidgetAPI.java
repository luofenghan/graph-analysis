package com.analysis.graph.web.api.controller;

import com.analysis.graph.common.domain.dbo.Widget;
import com.analysis.graph.web.library.repository.WidgetRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cwc on 2017/5/4 0004.
 */
@RestController
@RequestMapping("/api/graph")
public class WidgetAPI {

    @Resource
    private WidgetRepository graphRepository;

    @Resource
    private SessionRepository sessionRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Widget saveGraph(Widget graph) {
        return graphRepository.saveWidget(graph);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Widget updateGraph(Widget graph) {
        return graphRepository.updateWidget(graph);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Widget> getGraphList() {
        return graphRepository.listWidgetForClient(sessionRepository.getUserId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeGraph(@PathVariable Long id) {
        graphRepository.removeWidget(id);
    }

    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public List<String> getGraphCategoryList() {
        return graphRepository.listWidgetForClient(sessionRepository.getUserId())
                .stream()
                .map(Widget::getCategory)
                .collect(Collectors.toList());
    }
}
