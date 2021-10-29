package com.example.securityDemo;


import com.example.securitydemo.Service.UserService;
import com.example.securitydemo.entity.Role;
import com.example.securitydemo.entity.User;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static java.lang.Boolean.TRUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Log
public class SecurityWebAppTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Test
    public void buildData() throws Exception {
        userService.deleteAll();
        userService.saveRole(new Role(null,"ROLE_USER"));
        userService.saveRole(new Role(null,"ROLE_MANAGER"));
        userService.saveRole(new Role(null,"ROLE_ADMIN"));
        userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));

        userService.saveUser(new User(null,"user","123456","user@gmail.com",TRUE,new ArrayList<>()));
        userService.saveUser(new User(null,"manager","123456","manager@gmail.com",TRUE,new ArrayList<>()));
        userService.saveUser(new User(null,"admin","123456","admin@gmail.com",TRUE,new ArrayList<>()));
        userService.saveUser(new User(null,"superAdmin","123456","superadmin@gmail.com",TRUE,new ArrayList<>()));



        userService.addRoleToUser("user","ROLE_USER");
        userService.addRoleToUser("manager","ROLE_MANAGER");
        userService.addRoleToUser("admin","ROLE_USER");
        userService.addRoleToUser("admin","ROLE_ADMIN");
        userService.addRoleToUser("superAdmin","ROLE_SUPER_ADMIN");
    }

    @Test
    public void loginWithInvalidUserThenUnauthenticated() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
                .user("invalid")
                .password("invalidpassword");

        mockMvc.perform(login)
                .andExpect(unauthenticated());
        log.info("***************loginWithInvalidUserThenUnauthenticated ****************");
    }

    @Test
    public void accessUnsecuredResourceThenOk() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
        log.info("***************accessUnsecuredResourceThenOk ****************");
    }

    @Test
    public void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
        log.info("***************accessSecuredResourceUnauthenticatedThenRedirectsToLogin ****************");
    }
    @Test
    public void loginWithValidUserThenAuthenticated() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = formLogin()
                .user("user")
                .password("123456");
        mockMvc.perform(login)
                .andExpect(authenticated().withUsername("user"));
        log.info("***************loginWithValidUserThenAuthenticated Successes****************");
    }
    @Test
    @WithMockUser
    public void accessSecuredResourceAuthenticatedThenOk() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk());
        log.info("***************accessSecuredResourceAuthenticatedThenOk ****************");
    }
}
