package com.practice.todo.repository;

import com.practice.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
     User findByEmail(String email);

     Boolean existsByEmail(String email);
}
