package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.Dashboard;
import com.analysis.graph.config.DataConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cwc on 2017/5/7 0007.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataConfig.class})
@Transactional
public class DashboardRepositoryTest {

    @Resource
    private DashboardRepository dashboardRepository;

    private Dashboard dashboard() {
        Dashboard dashboard = new Dashboard();
        dashboard.setName("name");
        dashboard.setLayoutConfig("config");
        dashboard.setCategory("fenlei ");
        dashboard.setClientId(1);
        return dashboard;
    }

    @Test
    @Rollback
    public void saveDashboard() throws Exception {
        Dashboard dashboard = dashboard();
        Dashboard savedDashboard = dashboardRepository.saveDashboard(dashboard);
        Assert.assertNotNull(savedDashboard.getId());
    }

    @Test
    @Rollback
    public void updateDashboard() throws Exception {
        Dashboard dashboard = dashboard();
        Dashboard savedDashboard = dashboardRepository.saveDashboard(dashboard);
        Assert.assertNotNull(savedDashboard.getId());

        savedDashboard.setCategory("新分类");
        dashboardRepository.updateDashboard(savedDashboard);

        Dashboard newDash = dashboardRepository.getDashboard(savedDashboard.getId());
        Assert.assertEquals(savedDashboard.getCategory(), newDash.getCategory());
    }

    @Test(expected = IllegalArgumentException.class)
    @Rollback
    public void removeDashboard() throws Exception {
        Dashboard dashboard = dashboard();
        Dashboard savedDashboard = dashboardRepository.saveDashboard(dashboard);
        Assert.assertNotNull(savedDashboard.getId());

        dashboardRepository.removeDashboard(savedDashboard.getId());

        dashboardRepository.getDashboard(savedDashboard.getId());
    }

    @Test
    @Rollback
    public void listDashboardForClient() throws Exception {
        Dashboard dashboard = dashboard();
        Dashboard savedDashboard = dashboardRepository.saveDashboard(dashboard);
        Assert.assertNotNull(savedDashboard.getId());

        List<Dashboard> dashboardList = dashboardRepository.listDashboardForClient(savedDashboard.getClientId());
        Assert.assertEquals(dashboardList.size(), 1);
    }

}