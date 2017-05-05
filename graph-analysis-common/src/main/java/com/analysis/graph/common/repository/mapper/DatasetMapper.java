package com.analysis.graph.common.repository.mapper;

import com.analysis.graph.common.domain.dbo.Dataset;
import com.analysis.graph.common.domain.dbo.DatasetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface DatasetMapper {
    int countByExample(DatasetExample example);

    int deleteByExample(DatasetExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Dataset record);

    int insertSelective(Dataset record);

    List<Dataset> selectByExampleWithRowbounds(DatasetExample example, RowBounds rowBounds);

    List<Dataset> selectByExample(DatasetExample example);

    Dataset selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Dataset record, @Param("example") DatasetExample example);

    int updateByExample(@Param("record") Dataset record, @Param("example") DatasetExample example);

    int updateByPrimaryKeySelective(Dataset record);

    int updateByPrimaryKey(Dataset record);
}