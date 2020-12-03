package com.example.springtest.repositories;

import com.example.springtest.models.Post;
import com.example.springtest.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdEquals(Long id);
}
