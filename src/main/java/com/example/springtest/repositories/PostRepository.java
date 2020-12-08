package com.example.springtest.repositories;

import com.example.springtest.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByIdEquals(Long id);
    Post findByTitle(String title);
}