package com.example.UserManagement.controller;

import com.example.UserManagement.dto.RefreshTokenRequest;
import com.example.UserManagement.entity.RefreshToken;
import com.example.UserManagement.entity.User;
import com.example.UserManagement.security.JwtService;
import com.example.UserManagement.security.RefreshTokenService;
import com.example.UserManagement.service.UserService;
import com.example.UserManagement.util.LoginRequest;
import com.example.UserManagement.util.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authtenticationManager;

    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User users = userService.createUser(user);

        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authtenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUsername());

            return LoginResponse.builder()
                    .accessToken(jwtService.generateToken(loginRequest.getUsername()))
                    .jwtToken(refreshToken.getToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("invalid user");
        }

    }
    @PostMapping("/refreshToken")
    public LoginResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        return refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpirations)
                .map(RefreshToken::getUser)
                .map(user ->{
                    String generateToken = jwtService.generateToken(user.getEmail());
                    return LoginResponse.builder()
                            .accessToken(generateToken)
                            .jwtToken(refreshTokenRequest.getToken())

                            .build();
                }).orElseThrow(()-> new RuntimeException("refresh token is not present in db"));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId,@RequestBody User user) {
        User updateUserData = userService.updateUser(userId, user);
        return new ResponseEntity<>(updateUserData, HttpStatus.OK);
    }

    @GetMapping("/single/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")

    public User getSingleUser(@PathVariable Long userid) {
        User user = userService.getUserById(userid);
        return user;
    }

    @GetMapping("/alluser")
    @PreAuthorize("hasAuthority('NORMAL')")
    public List<User> findAllUser() {
        return userService.getAllUser();
    }
    @DeleteMapping("/delete/{userid}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }
    @GetMapping("/persons")
    public Map<String, List<User>> getPersonsGroupedByName() {
        return userService.groupingByName();
    }

}
