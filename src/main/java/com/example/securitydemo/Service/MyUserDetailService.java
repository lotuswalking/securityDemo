package com.example.securitydemo.Service;


import com.example.securitydemo.entity.MyUserDetails;
import com.example.securitydemo.entity.User;
import com.example.securitydemo.respo.UserRepository;
import com.example.securitydemo.util.jwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        MyUserDetails userDetails = null;
       if(user != null) {
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           userDetails = new MyUserDetails(user);
//           final String jwt = jwtUtil.generateToken(userDetails);

       }else{
           throw new UsernameNotFoundException("Uer Not exists with the name"+ username);
       }
       return userDetails;
    }

}
