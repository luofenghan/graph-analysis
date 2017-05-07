package com.analysis.graph.common.repository.mapper;

import com.analysis.graph.common.domain.dbo.ClientRole;
import com.analysis.graph.common.domain.dbo.ClientRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ClientRoleMapper {
    int countByExample(ClientRoleExample example);

    int deleteByExample(ClientRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ClientRole record);

    int insertSelective(ClientRole record);

    List<ClientRole> selectByExampleWithRowbounds(ClientRoleExample example, RowBounds rowBounds);

    List<ClientRole> selectByExample(ClientRoleExample example);

    ClientRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ClientRole record, @Param("example") ClientRoleExample example);

    int updateByExample(@Param("record") ClientRole record, @Param("example") ClientRoleExample example);

    int updateByPrimaryKeySelective(ClientRole record);

    int updateByPrimaryKey(ClientRole record);
}