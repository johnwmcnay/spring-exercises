package com.example.springtest.controllers;

import com.example.springtest.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PostController {

    @GetMapping("/posts")
    public String index(Model model) {

        List<Post> posts = new ArrayList<>(Arrays.asList(
                new Post("title1", "body1"),
                new Post("title2", "body2")
        ));

        model.addAttribute("posts", posts);

        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable int id, Model model) {

        Post post = new Post("title individual", "body individual");
        model.addAttribute("post", post);

        return "posts/show";
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String createPost() {
        return "view the form for creating a post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String func() {
        return "create a new post";
    }

}
