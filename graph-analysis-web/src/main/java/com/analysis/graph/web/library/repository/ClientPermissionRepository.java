package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.ClientRole;
import com.analysis.graph.common.domain.dbo.ClientRoleExample;
import com.analysis.graph.common.domain.dbo.Role;
import com.analysis.graph.common.domain.dbo.RoleExample;
import com.analysis.graph.common.repository.mapper.ClientRoleMapper;
import com.analysis.graph.common.repository.mapper.RoleMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by cwc on 2017/5/7 0007.
 */
@Repository
public class ClientPermissionRepository {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private ClientRoleMapper clientRoleMapper;

    public List<ClientRole> listRoleForClient(Integer id) {
        ClientRoleExample example = new ClientRoleExample();
        example.createCriteria().andClientIdEqualTo(id);
        return clientRoleMapper.selectByExample(example);
    }


    public List<Role> listRole(List<String> ids) {
        RoleExample example = new RoleExample();
        example.createCriteria().andIdIn(ids);
        return roleMapper.selectByExample(example);
    }
}
