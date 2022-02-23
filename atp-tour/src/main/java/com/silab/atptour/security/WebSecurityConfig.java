package com.silab.atptour.security;

import com.silab.atptour.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Lazar
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String defaultUser = "USER";

    private final String adminUser = "ADMIN";

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                //user
                .antMatchers(HttpMethod.PUT, "/user").hasAnyAuthority(adminUser)
                .antMatchers("/user/**").permitAll()
                //country
                .antMatchers("/country").hasAuthority(adminUser)
                //tournament  
                .antMatchers(HttpMethod.DELETE, "/tournament/**").hasAuthority(adminUser)
                .antMatchers(HttpMethod.GET, "/tournament/**").hasAnyAuthority(adminUser, defaultUser)
                .antMatchers("/tournament").hasAuthority(adminUser)
                //player   
                .antMatchers(HttpMethod.GET, "/player/**").hasAnyAuthority(adminUser, defaultUser)
                .antMatchers("/player").hasAuthority(adminUser)
                //matches
                .antMatchers(HttpMethod.GET, "/matches/**/").hasAnyAuthority(adminUser, defaultUser)
                .antMatchers("/matches").hasAuthority(adminUser)
                //statistics
                .antMatchers("/statistics").hasAuthority(adminUser)
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}
