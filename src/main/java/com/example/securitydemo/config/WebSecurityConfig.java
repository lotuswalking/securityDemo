package com.example.securitydemo.config;


import com.example.securitydemo.filter.JWTAuthorizationFilter;
import com.example.securitydemo.filter.JWTUsernamePasswordAuthFilter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.persistence.GenerationType;

import static org.springframework.http.HttpMethod.GET;


@Log
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void setAuthenticationConfiguration(AuthenticationConfiguration authenticationConfiguration) {
        super.setAuthenticationConfiguration(authenticationConfiguration);
    }

    @Value("${application.auth}")
    private String authType;
    @Value("${spring.ldap.url}")
    private String ldapUrl;
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.csrf().disable();
//        JWTUsernamePasswordAuthFilter jwtUsernamePasswordAuthFilter = new JWTUsernamePasswordAuthFilter(authenticationManagerBean());
//        jwtUsernamePasswordAuthFilter.setFilterProcessesUrl("/api/login");
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/", "/home","/login","/api/login/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin().loginProcessingUrl("/api/login").defaultSuccessUrl("/hello").failureForwardUrl("/home");

//        http.addFilter(jwtUsernamePasswordAuthFilter);

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        if (authType.equalsIgnoreCase("LDAP")) {

            log.info("***********Using Local LDAP****************");
            auth
                    .ldapAuthentication()
                    .userSearchFilter("uid={0}")
                    .contextSource()
                    .url(ldapUrl);
        } else if("localDB".equalsIgnoreCase(authType)) {
            log.info("***********Using Local Database Users****************");
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());

        }
        else {
            super.configure(auth);
        }
    }

    @Override @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
