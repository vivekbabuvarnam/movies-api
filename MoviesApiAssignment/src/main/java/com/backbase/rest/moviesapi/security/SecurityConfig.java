package com.backbase.rest.moviesapi.security;

import javax.annotation.Resource;

import com.backbase.rest.moviesapi.filter.AuthorizationFilter;
import com.backbase.rest.moviesapi.filter.CustomAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource(name = "userServiceImpl")
    private UserDetailsService userDetailsService;
    @Resource(name = "passwordEncoder")
    private BCryptPasswordEncoder passwordEncoder;

    private static final String[] AUTH_WHITELIST = {
                    // -- Swagger UI v2
                    "/v3/api-docs",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**",
                    // -- Swagger UI v3 (OpenAPI)
                    "/v3/api-docs/**",
                    "/swagger-ui/**"
                    // other public endpoints of your API may be appended to this array
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //H2 Database, Token generation
        http.authorizeRequests().antMatchers("/login/**").permitAll();
        http.authorizeRequests().antMatchers("/console/**").permitAll();
        //For Swagger
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
        //For Movie API
        http.authorizeRequests().antMatchers("/rating/**","/movie/**").hasAnyAuthority("ROLE_USER","ROLE_MANAGER","ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/management/**").hasAnyAuthority("ROLE_MANAGER","ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/upload/**").hasAnyAuthority("ROLE_ADMIN");

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(customAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthorizationFilter customAuthorizationFilter() throws Exception {
        return new AuthorizationFilter();
    }

}
