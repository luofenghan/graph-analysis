package com.analysis.graph.web.library.domain;

import com.analysis.graph.common.domain.dbo.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

/**
 * Created by cwc on 2017/4/24 0024.
 */
public class SecurityUser extends User {
    private Integer id;

    public SecurityUser(Client client, GrantedAuthority... authorities) {
        super(client.getMobile(), client.getPassword(), Arrays.asList(authorities));
        this.id = client.getId();
    }

    public Integer getId() {
        return id;
    }
}
