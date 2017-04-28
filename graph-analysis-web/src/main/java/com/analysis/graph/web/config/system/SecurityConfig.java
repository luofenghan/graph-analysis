package com.analysis.graph.web.config.system;

import com.alibaba.fastjson.JSON;
import com.analysis.graph.common.constant.Encryption;
import com.analysis.graph.common.constant.RoleEnum;
import com.analysis.graph.common.domain.dbo.Client;
import com.analysis.graph.common.domain.dto.ErrorDTO;
import com.analysis.graph.web.library.domain.SecurityUser;
import com.analysis.graph.web.library.repository.ClientRepository;
import com.analysis.graph.web.library.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


/**
 * Created by cwc on 2017/4/19 0019.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${server.session.cookie.name:graph-analysis-cookie}")
    private String serverSessionCookieName;


    @Autowired
    private ClientRepository clientRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return mobile -> {
            Optional<Client> clientOptional = clientRepository.getClientByMobile(mobile);
            if (!clientOptional.isPresent()) {
                throw new UsernameNotFoundException(String.format("Client %s not found", mobile));
            }
            Client client = clientOptional.get();

            return new SecurityUser(client, new SimpleGrantedAuthority(RoleEnum.CLIENT.name()));
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
                        httpServletResponse.setContentType("application/json");
                        ErrorDTO errorDTO = new ErrorDTO();
                        errorDTO.setStatus(HttpStatus.FORBIDDEN.name());
                        errorDTO.setCode(HttpStatus.FORBIDDEN.value());
                        errorDTO.setTitle(String.format("%s access denied", httpServletRequest.getRequestURI()));
                        errorDTO.setDetail(e.getMessage());
                        httpServletResponse.getWriter().write(JSON.toJSONString(errorDTO));
                    }
                });
    }
}