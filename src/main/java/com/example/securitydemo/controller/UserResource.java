package com.example.securitydemo.controller;

import com.example.securitydemo.entity.Role;
import com.example.securitydemo.entity.User;
import com.example.securitydemo.Service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class UserResource {
    private final UserService userService;
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUser() {
        return ResponseEntity.ok().body(userService.getUsers());

    }
    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));

    }
    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));

    }
    @PostMapping("/role/addtouser")
    public ResponseEntity<?> saveRole(@RequestBody RoleToUserForm roleToUserForm) {
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/addtouser").toUriString());
        userService.addRoleToUser(roleToUserForm.getUsername(),roleToUserForm.getRoleName());
        return ResponseEntity.ok().build();

    }


}
@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}