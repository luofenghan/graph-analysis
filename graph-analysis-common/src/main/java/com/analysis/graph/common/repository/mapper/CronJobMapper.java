package com.analysis.graph.common.repository.mapper;

import com.analysis.graph.common.domain.dbo.CronJob;
import com.analysis.graph.common.domain.dbo.CronJobExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CronJobMapper {
    int countByExample(CronJobExample example);

    int deleteByExample(CronJobExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CronJob record);

    int insertSelective(CronJob record);

    List<CronJob> selectByExampleWithBLOBsWithRowbounds(CronJobExample example, RowBounds rowBounds);

    List<CronJob> selectByExampleWithBLOBs(CronJobExample example);

    List<CronJob> selectByExampleWithRowbounds(CronJobExample example, RowBounds rowBounds);

    List<CronJob> selectByExample(CronJobExample example);

    CronJob selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CronJob record, @Param("example") CronJobExample example);

    int updateByExampleWithBLOBs(@Param("record") CronJob record, @Param("example") CronJobExample example);

    int updateByExample(@Param("record") CronJob record, @Param("example") CronJobExample example);

    int updateByPrimaryKeySelective(CronJob record);

    int updateByPrimaryKeyWithBLOBs(CronJob record);

    int updateByPrimaryKey(CronJob record);
}