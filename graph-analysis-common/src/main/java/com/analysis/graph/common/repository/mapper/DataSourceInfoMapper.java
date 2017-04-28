package com.analysis.graph.common.repository.mapper;

import com.analysis.graph.common.domain.dbo.DataSourceInfo;
import com.analysis.graph.common.domain.dbo.DataSourceInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DataSourceInfoMapper {
    int countByExample(DataSourceInfoExample example);

    int deleteByExample(DataSourceInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DataSourceInfo record);

    int insertSelective(DataSourceInfo record);

    List<DataSourceInfo> selectByExampleWithRowbounds(DataSourceInfoExample example, RowBounds rowBounds);

    List<DataSourceInfo> selectByExample(DataSourceInfoExample example);

    DataSourceInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DataSourceInfo record, @Param("example") DataSourceInfoExample example);

    int updateByExample(@Param("record") DataSourceInfo record, @Param("example") DataSourceInfoExample example);

    int updateByPrimaryKeySelective(DataSourceInfo record);

    int updateByPrimaryKey(DataSourceInfo record);
}