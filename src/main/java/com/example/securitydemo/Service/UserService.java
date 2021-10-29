package com.example.securitydemo.Service;

import com.example.securitydemo.entity.Role;
import com.example.securitydemo.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String username);
    List<User> getUsers();

    void deleteAll();
}
