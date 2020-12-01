package com.example.springtest.controllers;

import com.example.springtest.models.Post;
import com.example.springtest.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/posts")
    public String index(Model model) {

        List<Post> posts = new ArrayList<>(Arrays.asList(
                new Post("title1", "body1"),
                new Post("title2", "body2")
        ));

        System.out.println("postDao.findAll() = " + postDao.findAll());

        model.addAttribute("posts", postDao.findAll());

        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable Long id, Model model) {

        model.addAttribute("post", postDao.findByIdEquals(id));

        return "posts/show";
    }

    @GetMapping("/posts/form")
    public String postForm() {
        return "/posts/form";
    }

    @PostMapping("/posts/create")
    public String createPost(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body)
    {
        Post post = new Post(title, body);

        System.out.println("post = " + post);

        Post dbPost = postDao.save(post);

        System.out.println("dbPost = " + dbPost);
        System.out.println("dbPost.getId() = " + dbPost.getId());

        return "redirect:/posts/" + dbPost.getId().toString();

    }

}
