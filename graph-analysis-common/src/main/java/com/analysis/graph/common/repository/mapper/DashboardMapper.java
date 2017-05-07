package com.analysis.graph.common.repository.mapper;

import com.analysis.graph.common.domain.dbo.Dashboard;
import com.analysis.graph.common.domain.dbo.DashboardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DashboardMapper {
    int countByExample(DashboardExample example);

    int deleteByExample(DashboardExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Dashboard record);

    int insertSelective(Dashboard record);

    List<Dashboard> selectByExampleWithBLOBsWithRowbounds(DashboardExample example, RowBounds rowBounds);

    List<Dashboard> selectByExampleWithBLOBs(DashboardExample example);

    List<Dashboard> selectByExampleWithRowbounds(DashboardExample example, RowBounds rowBounds);

    List<Dashboard> selectByExample(DashboardExample example);

    Dashboard selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Dashboard record, @Param("example") DashboardExample example);

    int updateByExampleWithBLOBs(@Param("record") Dashboard record, @Param("example") DashboardExample example);

    int updateByExample(@Param("record") Dashboard record, @Param("example") DashboardExample example);

    int updateByPrimaryKeySelective(Dashboard record);

    int updateByPrimaryKeyWithBLOBs(Dashboard record);

    int updateByPrimaryKey(Dashboard record);
}