package com.example.UserManagement.config;

import com.example.UserManagement.entity.User;
import com.example.UserManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> email = userRepository.findByEmail(username);
        return email.map(CustomUserDetail::new).orElseThrow(() -> new UsernameNotFoundException("user not found" + username));

    }
}
