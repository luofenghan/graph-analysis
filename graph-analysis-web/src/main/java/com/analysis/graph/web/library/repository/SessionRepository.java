package com.analysis.graph.web.library.repository;

import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.web.library.domain.SecurityUser;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Created by cwc on 2017/4/22 0022.
 */
@Repository
public class SessionRepository {
    @Resource
    @Lazy
    private ClientRepository clientRepository;

    public Client getCurrentOnlineClient() {
        return clientRepository.getClientByMobile(getSecurityUser().getUsername());
    }

    public Integer getUserId() {
        return getSecurityUser().getId();
    }

    private SecurityUser getSecurityUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof SecurityUser) {
                return (SecurityUser) authentication.getPrincipal();
            }
        }
        throw new IllegalStateException("User not found!");
    }
}
