package com.analysis.graph.common.repository.mapper;

import com.analysis.graph.common.domain.dbo.Cronjob;
import com.analysis.graph.common.domain.dbo.CronjobExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CronjobMapper {
    int countByExample(CronjobExample example);

    int deleteByExample(CronjobExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Cronjob record);

    int insertSelective(Cronjob record);

    List<Cronjob> selectByExampleWithBLOBsWithRowbounds(CronjobExample example, RowBounds rowBounds);

    List<Cronjob> selectByExampleWithBLOBs(CronjobExample example);

    List<Cronjob> selectByExampleWithRowbounds(CronjobExample example, RowBounds rowBounds);

    List<Cronjob> selectByExample(CronjobExample example);

    Cronjob selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Cronjob record, @Param("example") CronjobExample example);

    int updateByExampleWithBLOBs(@Param("record") Cronjob record, @Param("example") CronjobExample example);

    int updateByExample(@Param("record") Cronjob record, @Param("example") CronjobExample example);

    int updateByPrimaryKeySelective(Cronjob record);

    int updateByPrimaryKeyWithBLOBs(Cronjob record);

    int updateByPrimaryKey(Cronjob record);
}