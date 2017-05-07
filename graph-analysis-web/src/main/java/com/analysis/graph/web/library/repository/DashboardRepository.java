package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.constant.GlobalConstant;
import com.analysis.graph.common.domain.dbo.Dashboard;
import com.analysis.graph.common.domain.dbo.DashboardExample;
import com.analysis.graph.common.repository.mapper.DashboardMapper;
import com.google.common.base.Preconditions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by cwc on 2017/5/7 0007.
 */
@Repository
public class DashboardRepository {

    @Resource
    private DashboardMapper dashboardMapper;

    @Transactional
    public Dashboard saveDashboard(Dashboard dashboard) {
        sanitize(dashboard);
        Date now = new DateTime().toDate();
        dashboard.setCreatedTime(now);
        dashboard.setUpdatedTime(now);
        dashboardMapper.insert(dashboard);
        return dashboard;
    }

    @Transactional
    public Dashboard updateDashboard(Dashboard dashboard) {
        sanitize(dashboard);
        dashboard.setUpdatedTime(new DateTime().toDate());
        dashboardMapper.updateByPrimaryKeySelective(dashboard);
        return dashboard;
    }

    @Transactional
    public void removeDashboard(Long id) {
        dashboardMapper.deleteByPrimaryKey(id);
    }

    public List<Dashboard> listDashboardForClient(Integer clientId) {
        DashboardExample example = new DashboardExample();
        example.createCriteria().andClientIdEqualTo(clientId);
        return dashboardMapper.selectByExampleWithBLOBs(example);
    }


    public Dashboard getDashboard(Long id) {
        Dashboard dashboard = dashboardMapper.selectByPrimaryKey(id);
        Preconditions.checkArgument(dashboard != null, "can not find dashboard by id:" + id);
        return dashboard;
    }

    private void sanitize(Dashboard dashboard) {
        if (Objects.isNull(dashboard.getCategory())) {
            dashboard.setCategory(GlobalConstant.UN_CATEGORY);
        }

    }
}
