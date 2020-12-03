package com.example.springtest.controllers;

import com.example.springtest.models.Post;
import com.example.springtest.models.User;
import com.example.springtest.repositories.PostRepository;
import com.example.springtest.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;

    public PostController(PostRepository postDao, UserRepository userDao)
    {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping("/posts")
    public String index(Model model) {

        model.addAttribute("posts", postDao.findAll());

        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable Long id, Model model) {

        Post post = postDao.findByIdEquals(id);

        model.addAttribute("post", post);
        model.addAttribute("id", id);
        model.addAttribute("user", userDao.findByIdEquals(post.getUser().getId()));

        return "posts/show";
    }

    @GetMapping("/posts/form")
    public String postForm() {
        return "/posts/form";
    }

    @GetMapping("/posts/edit")
    public String editForm(
            @RequestParam(name = "id") Long id,
            Model model) {

        model.addAttribute("post", postDao.findByIdEquals(id));

        return "/posts/edit";
    }

    @PostMapping("/posts/edit")
    public String updatePost(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body) {

        Post post = new Post(id, title, body);
        postDao.save(post);
        return "redirect:/posts/" + id.toString();
    }

    @PostMapping("/posts/create")
    public String createPost(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "body") String body) {
        User user = userDao.findByIdEquals(1L);

        Post post = new Post(title, body, user);
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
