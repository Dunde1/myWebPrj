package org.me.springboot.web;

import lombok.RequiredArgsConstructor;
import org.me.springboot.config.auth.LoginUser;
import org.me.springboot.config.auth.dto.SessionUser;
import org.me.springboot.service.posts.PostsService;
import org.me.springboot.web.dto.PostsListResponseDto;
import org.me.springboot.web.dto.PostsResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("user", user);
        }
        return "/index";
    }
    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/error")
    public String error(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("user", user);
        }
        return "/error";
    }
    @GetMapping("/posts/list")
    public String postsMain(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

        if(user != null) {
            model.addAttribute("user", user);
        }
        return "/posts/list/1";
    }
    @GetMapping("/posts/list/{page}")
    public String postsList(@PathVariable int page, Model model, @LoginUser SessionUser user){
        List<PostsListResponseDto> postsList = postsService.findAllDesc();

        if((page-1)*10>postsList.size()) return "/error";

        class pageNumberC {
            int pgNum;
            pageNumberC(int i){ this.pgNum = i;}
        }

        List<PostsListResponseDto> newList = new ArrayList<>();
        List<pageNumberC> pageNumber = new ArrayList<>();

        for (int i = (page-1)*10+1; i <= page*10; i++) {
            if(postsList.size()<i) break;
            newList.add(postsList.get(i-1));
        }

        int startPage = ((page-1)/10)*10+1;
        int endPage = startPage+9;
        int lastPage = (int)Math.ceil(postsList.size()/10.0);

        if((endPage-1)*10>postsList.size()) endPage = lastPage;

        for (int i = startPage; i <= endPage; i++) pageNumber.add(new pageNumberC(i));

        if(startPage != 1) model.addAttribute("prevPage", ((page-11)/10)*10+10);
        if(endPage != lastPage) model.addAttribute("nextPage", ((page-1)/10)*10+11>lastPage?lastPage:((page-1)/10)*10+11);

        model.addAttribute("posts", newList);
        model.addAttribute("thisPage", page);
        model.addAttribute("pageNumber", pageNumber);

        if(user != null) {
            model.addAttribute("user", user);
        }
        return "/posts/posts-list";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        if(user != null) {
            model.addAttribute("user", user);
        }
        return "/posts/posts-update";
    }
}
