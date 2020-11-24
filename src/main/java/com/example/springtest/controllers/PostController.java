package com.example.springtest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {
//    GET	/posts	posts index page
//    GET	/posts/{id}	view an individual post
//    GET	/posts/create	view the form for creating a post
//    POST	/posts/create	create a new post

    @GetMapping("/posts")
    @ResponseBody
    public String index() {
        return "posts index page";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String viewPost(@PathVariable int id) {
        return "view and individual post: " + id;
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
