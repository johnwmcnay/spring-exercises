package com.example.springtest.controllers;

import com.example.springtest.models.Post;
import com.example.springtest.models.User;
import com.example.springtest.repositories.PostRepository;
import com.example.springtest.repositories.UserRepository;
import com.example.springtest.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserRepository userDao;
    private final EmailService emailService;

    public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.emailService = emailService;
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

    @GetMapping("/posts/create")
    public String postForm(Model model) {

        Post post = new Post();

        model.addAttribute("post", post);
        model.addAttribute("action", "/posts/create");
        model.addAttribute("pageTitle", "Create a post");
        model.addAttribute("postTitle", "");
        model.addAttribute("postBody", "");

        return "posts/form";
    }

    @GetMapping("/posts/{id}/edit")
    public String editForm(
            @PathVariable(name = "id") Long id,
            Model model) {

        Post post = postDao.findByIdEquals(id);
        model.addAttribute("action", "/posts/" + id.toString() + "/edit");
        model.addAttribute("pageTitle", "Edit your post");
        model.addAttribute("post", post);

        return "posts/form";
    }

    @PostMapping("/posts/{id}/edit")
    public String updatePost(
            @PathVariable(name = "id") Long id,
            @ModelAttribute Post post) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user != null) {
            post.setUser(user);
            post.setId(id);
            postDao.save(post);
        }

        return "redirect:/posts/" + id.toString();
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        post.setUser(user);

        Post dbPost = postDao.save(post);
        this.emailService.prepareAndSend(post, "You made a new post: " + post.getTitle(), "This is to notify you of your new post.");

        return "redirect:/posts/" + dbPost.getId().toString();
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(
            @PathVariable(name = "id") Long id) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getId().equals(id)) {
            postDao.delete(postDao.findByIdEquals(id));
        }

        return "redirect:/posts";
    }

}
