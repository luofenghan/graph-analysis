package com.analysis.graph.web.config.system.security;

import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dbo.Role;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by cwc on 2017/4/24 0024.
 */
public class AuthorizedUser implements UserDetails, CredentialsContainer {
    private Integer id;
    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;

    public AuthorizedUser(Client client, Set<GrantedAuthority> authorities) {
        this.id = client.getId();
        this.username = client.getMobile();
        this.password = client.getPassword();
        this.authorities = authorities;
    }

    public Integer getId() {
        return id;
    }

    public Set<String> getResources() {
        if (CollectionUtils.isEmpty(this.authorities)) {
            return Collections.emptySet();
        }
        return this.authorities.stream()
                .flatMap(grantedAuthority -> UserAuthority.class.cast(grantedAuthority).getResources().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static class UserAuthority implements GrantedAuthority {
        private String role;
        private Set<String> resources;

        public UserAuthority(Role role) {
            this.role = role.getId().toUpperCase();
            StringTokenizer itr = new StringTokenizer(role.getResourceList(), ",");
            resources = new HashSet<>();
            while (itr.hasMoreTokens()) {
                resources.add(itr.nextToken().trim());
            }
        }

        public UserAuthority(String role, Set<String> resources) {
            this.role = role;
            this.resources = resources;
        }

        @Override
        public String getAuthority() {
            return role;
        }

        public Set<String> getResources() {
            return resources;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UserAuthority that = (UserAuthority) o;

            if (role != null ? !role.equals(that.role) : that.role != null) return false;
            return resources != null ? resources.equals(that.resources) : that.resources == null;
        }

        @Override
        public int hashCode() {
            int result = role != null ? role.hashCode() : 0;
            result = 31 * result + (resources != null ? resources.hashCode() : 0);
            return result;
        }
    }
}
