package com.analysis.graph.web.api.controller;

import com.analysis.graph.common.domain.dbo.Dashboard;
import com.analysis.graph.web.library.repository.DashboardRepository;
import com.analysis.graph.web.library.repository.SessionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cwc on 2017/5/7 0007.
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardAPI {

    @Resource
    private DashboardRepository dashboardRepository;

    @Resource
    private SessionRepository sessionRepository;

    @RequestMapping(method = RequestMethod.POST)
    public Dashboard saveDashboard(Dashboard dashboard) {
        dashboard.setClientId(sessionRepository.getUserId());
        return dashboardRepository.saveDashboard(dashboard);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Dashboard updateDashboard(Dashboard dashboard) {
        return dashboardRepository.updateDashboard(dashboard);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteDashboard(@PathVariable Long id) {
        dashboardRepository.removeDashboard(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Dashboard> getDashboardListForClient() {
        return dashboardRepository.listDashboardForClient(sessionRepository.getUserId());
    }
}
