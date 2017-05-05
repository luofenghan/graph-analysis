package com.analysis.graph.web.api.controller;

import com.analysis.graph.common.domain.dbo.Graph;
import com.analysis.graph.web.library.repository.GraphRepository;
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
public class GraphAPI {

    @Resource
    private GraphRepository graphRepository;

    @Resource
    private SessionRepository sessionRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Graph saveGraph(Graph graph) {
        return graphRepository.insertGraph(graph);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Graph updateGraph(Graph graph) {
        return graphRepository.updateGraph(graph);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Graph> getGraphList() {
        return graphRepository.listGraph(sessionRepository.getUserId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void removeGraph(@PathVariable Long id) {
        graphRepository.deleteGraph(id);
    }

    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public List<String> getGraphCategoryList() {
        return graphRepository.listGraph(sessionRepository.getUserId())
                .stream()
                .map(Graph::getCategory)
                .collect(Collectors.toList());
    }
}
