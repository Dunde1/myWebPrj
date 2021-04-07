package org.me.springboot.web;

import lombok.RequiredArgsConstructor;
import org.me.springboot.config.auth.LoginUser;
import org.me.springboot.config.auth.dto.SessionUser;
import org.me.springboot.service.posts.PostsService;
import org.me.springboot.web.dto.PostsResponseDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        if(user != null) {
            model.addAttribute("user", user.getName());
        }
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String error(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("user", user.getName());
        }
        return "error";
    }

    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user){
        if(user != null) {
            model.addAttribute("user", user.getName());
        }
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        if(user != null) {
            model.addAttribute("user", user.getName());
        }
        return "posts-update";
    }
}
