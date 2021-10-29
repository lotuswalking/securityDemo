package com.example.securitydemo;

import com.example.securitydemo.entity.Role;
import com.example.securitydemo.entity.User;
import com.example.securitydemo.respo.UserRepository;
import com.example.securitydemo.Service.UserService;
import com.example.securitydemo.util.RoleType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;

import static java.lang.Boolean.TRUE;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }

}
