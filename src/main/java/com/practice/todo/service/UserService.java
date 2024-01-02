package com.practice.todo.service;

import com.practice.todo.model.User;
import com.practice.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User createUser(User user){
        return userRepository.save(user);
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public Boolean checkIfUserAlreadyExists(String email){
        return userRepository.existsByEmail(email);
    }

}
