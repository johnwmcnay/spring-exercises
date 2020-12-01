package com.example.springtest.controllers;

import com.example.springtest.models.Post;
import com.example.springtest.repositories.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/posts")
    public String index(Model model) {

        model.addAttribute("posts", postDao.findAll());

        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable Long id, Model model) {

        model.addAttribute("post", postDao.findByIdEquals(id));
        model.addAttribute("id", id);

        return "posts/show";
    }

    @GetMapping("/posts/form")
    public String postForm() {
        return "/posts/form";
    }

    @GetMapping("/posts/edit")
    public String editForm() {
        return "/posts/edit";
    }

    @PostMapping("/posts/create")
    public String createPost(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body) {
        Post post = new Post(title, body);
        Post dbPost = postDao.save(post);

        return "redirect:/posts/" + dbPost.getId().toString();

    }

    @PostMapping("/posts/delete")
    public String deletePost(
            @RequestParam(name = "id") Long id) {

        postDao.delete(postDao.findByIdEquals(id));

        return "redirect:/posts";
    }

}
