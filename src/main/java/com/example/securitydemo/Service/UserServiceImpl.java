package com.example.securitydemo.Service;

import com.example.securitydemo.entity.Role;
import com.example.securitydemo.entity.User;
import com.example.securitydemo.respo.RoleRepository;
import com.example.securitydemo.respo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service  @RequiredArgsConstructor @Transactional  @Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info(String.format("Username:%s , RoleName: %s",username,roleName));
        if(username == null || username.isEmpty() || roleName == null || roleName.isEmpty()) {
            return;
        }
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);

        user.getRoles().add(role);

    }
    public void deleteAll() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }
}
