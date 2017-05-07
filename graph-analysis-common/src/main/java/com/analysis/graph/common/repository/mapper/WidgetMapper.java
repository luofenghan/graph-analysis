package com.analysis.graph.common.repository.mapper;

import com.analysis.graph.common.domain.dbo.Widget;
import com.analysis.graph.common.domain.dbo.WidgetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WidgetMapper {
    int countByExample(WidgetExample example);

    int deleteByExample(WidgetExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Widget record);

    int insertSelective(Widget record);

    List<Widget> selectByExampleWithRowbounds(WidgetExample example, RowBounds rowBounds);

    List<Widget> selectByExample(WidgetExample example);

    Widget selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Widget record, @Param("example") WidgetExample example);

    int updateByExample(@Param("record") Widget record, @Param("example") WidgetExample example);

    int updateByPrimaryKeySelective(Widget record);

    int updateByPrimaryKey(Widget record);
}