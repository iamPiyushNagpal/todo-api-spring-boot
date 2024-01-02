package com.practice.todo.service;

import com.practice.todo.exception.DuplicateUserException;
import com.practice.todo.model.LoginRequest;
import com.practice.todo.model.User;
import com.practice.todo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public User signup(User user){
        Boolean checkIfUserExists = userService.checkIfUserAlreadyExists(user.getEmail());
        if(checkIfUserExists)
            throw new DuplicateUserException("User with entered email already exists.");
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userService.createUser(user);
    }

    public String login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        System.out.println(authentication.isAuthenticated());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());
        return jwtUtil.generateToken(userDetails);
    }

}
