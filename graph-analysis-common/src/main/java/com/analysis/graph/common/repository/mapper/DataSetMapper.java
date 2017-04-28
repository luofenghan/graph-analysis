package com.analysis.graph.common.repository.mapper;

import com.analysis.graph.common.domain.dbo.DataSet;
import com.analysis.graph.common.domain.dbo.DataSetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DataSetMapper {
    int countByExample(DataSetExample example);

    int deleteByExample(DataSetExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DataSet record);

    int insertSelective(DataSet record);

    List<DataSet> selectByExampleWithRowbounds(DataSetExample example, RowBounds rowBounds);

    List<DataSet> selectByExample(DataSetExample example);

    DataSet selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DataSet record, @Param("example") DataSetExample example);

    int updateByExample(@Param("record") DataSet record, @Param("example") DataSetExample example);

    int updateByPrimaryKeySelective(DataSet record);

    int updateByPrimaryKey(DataSet record);
}