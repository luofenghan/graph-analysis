package com.analysis.graph.common.repository.mapper;

import com.analysis.graph.common.domain.dbo.Graph;
import com.analysis.graph.common.domain.dbo.GraphExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface GraphMapper {
    int countByExample(GraphExample example);

    int deleteByExample(GraphExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Graph record);

    int insertSelective(Graph record);

    List<Graph> selectByExampleWithRowbounds(GraphExample example, RowBounds rowBounds);

    List<Graph> selectByExample(GraphExample example);

    Graph selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Graph record, @Param("example") GraphExample example);

    int updateByExample(@Param("record") Graph record, @Param("example") GraphExample example);

    int updateByPrimaryKeySelective(Graph record);

    int updateByPrimaryKey(Graph record);
}