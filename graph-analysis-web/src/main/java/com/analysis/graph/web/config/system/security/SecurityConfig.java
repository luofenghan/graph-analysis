package com.analysis.graph.web.config.system.security;

import com.analysis.graph.common.constant.Encryption;
import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.web.config.system.security.AuthorizedUser.UserAuthority;
import com.analysis.graph.web.library.repository.ClientPermissionRepository;
import com.analysis.graph.web.library.repository.ClientRepository;
import com.analysis.graph.web.library.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.Collections;


/**
 * Created by cwc on 2017/4/19 0019.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${server.session.cookie.name:graph-analysis-cookie}")
    private String serverSessionCookieName;

    @Autowired
    private ClientRepository clientRepository;

    @Resource
    private ClientPermissionRepository clientPermissionRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return mobile -> {
            try {
                Client client = clientRepository.getClientByMobile(mobile);
                /*List<ClientRole> clientRoleList = clientPermissionRepository.listRoleForClient(client.getId());
                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                if (!CollectionUtils.isEmpty(clientRoleList)) {
                    List<Role> roles = clientPermissionRepository.listRole(clientRoleList.stream().map(ClientRole::getRoleId).collect(Collectors.toList()));
                    for (Role role : roles) {
                        grantedAuthorities.add(new UserAuthority(role));
                    }
                } else {
                    grantedAuthorities.add(new UserAuthority("ANONYMOUS", Collections.emptySet()));
                }*/
                return new AuthorizedUser(client, Collections.singleton(new UserAuthority("ADMIN", Collections.singleton("/api/**"))));
            } catch (IllegalArgumentException e) {
                throw new UsernameNotFoundException(String.format("Client %s not found", mobile), e);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(Encryption.PASSWORD_ENCODER_STRENGTH);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/assets/**")
                .antMatchers("/scripts/**")
                .antMatchers("/bower_components/**");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/register").anonymous()
                .antMatchers("/api/account/register").anonymous()
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .loginProcessingUrl("/perform_login")
                .successForwardUrl("/")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true");

        http.logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies(serverSessionCookieName)
                .logoutSuccessUrl("/login");


        http.csrf()
                .disable();

        http.exceptionHandling()
                .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
                    if (HttpUtils.isAjaxRequest(httpServletRequest)) {
                        HttpUtils.writeResponse(httpServletResponse, HttpStatus.FORBIDDEN,
                                String.format("%s access denied", httpServletRequest.getRequestURI()));
                    }
                });
    }
}