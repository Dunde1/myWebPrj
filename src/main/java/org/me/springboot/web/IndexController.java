package org.me.springboot.web;

import lombok.RequiredArgsConstructor;
import org.me.springboot.config.auth.LoginUser;
import org.me.springboot.config.auth.dto.SessionUser;
import org.me.springboot.service.function.bitcoin.BitcoinService;
import org.me.springboot.service.posts.PostsService;
import org.me.springboot.service.posts.comments.CommentsService;
import org.me.springboot.web.dto.posts.PostsListResponseDto;
import org.me.springboot.web.dto.posts.PostsResponseDto;
import org.me.springboot.web.dto.posts.comments.CommentsListResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {
    /** 메인화면에서 이동을 담당하는 컨트롤러 입니다.
     *
     * index() : 메인 화면으로 이동합니다.
     * login() : 로그인 창으로 이동합니다.
     * postsMain() : 게시판 페이지로 이동합니다. 첫 화면에서 게시글 리스트를 가져옵니다. (postsList()를 호출하게 됩니다.)
     *      이 페이지에서 글 쓰기가 가능합니다.
     * postsList() : 게시판 메인화면에서 몇번째 페이지의 리스트를 가져올 것인지 정하고 화면을 띄웁니다.
     * postsUpdate() : 게시판 상세 정보 페이지를 띄웁니다. 이 페이지에서 게시글과 코멘트의 수정, 삭제가 가능합니다.
     * testApiMain() : Api를 테스트해 볼 수 있는 페이지로 이동합니다.
     * lottoMain() : 타 프로젝트와 연동하여 사용하는 로또추첨 페이지로 이동합니다.
     * bitcoinMain() : 실제 비트코인 거래소의 Api를 이용한 가상 비트코인 거래소 페이지로 이동합니다.
     */
    private final PostsService postsService;
    private final CommentsService commentsService;
    private final BitcoinService bitcoinService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("user", user);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/posts/list")
    public String postsMain(Model model, @LoginUser SessionUser user){
        if(user != null) {
            model.addAttribute("user", user);
        }
        return "/posts/list/none/none/1";
    }

    @GetMapping("/posts/list/{filter}/{word}/{page}")
    public String postsList(@PathVariable String filter, @PathVariable String word, @PathVariable int page, Model model, @LoginUser SessionUser user){
        if(!(filter.equals("none")||filter.equals("title")||filter.equals("author"))) return "error";

        List<PostsListResponseDto> postsList;
        int allRowCount = 0;

        if(filter.equals("none")){
            postsList = postsService.findPageDesc((page-1)*10);
            allRowCount = postsService.allRowCount();
        }else {
            postsList = postsService.findPageDesc((page-1)*10, filter, word);
            allRowCount = postsService.allRowCount(filter, word);
        }

        if((page-1)*10>allRowCount) return "error";

        class pageNumberC {
            int pgNum;
            boolean isThisPage;
            pageNumberC(int i){
                this.pgNum = i;
                this.isThisPage = false;
            }
        }

        int startPage = ((page-1)/10)*10+1;
        int endPage = startPage+9;
        int lastPage = (int)Math.ceil(allRowCount/10.0);

        if((endPage-1)*10>allRowCount) endPage = lastPage;

        List<pageNumberC> pageNumber = new ArrayList<>();
        for (int i = startPage; i <= endPage; i++) {
            pageNumberC tmp = new pageNumberC(i);
            if(i==page) tmp.isThisPage=true;
            pageNumber.add(tmp);
        }

        if(startPage != 1) model.addAttribute("prevPage", ((page-11)/10)*10+10);
        if(endPage != lastPage) model.addAttribute("nextPage", ((page-1)/10)*10+11>lastPage?lastPage:((page-1)/10)*10+11);

        model.addAttribute("filter", filter);
        model.addAttribute("word", word);
        model.addAttribute("posts", postsList);
        model.addAttribute("thisPage", page);
        model.addAttribute("pageNumber", pageNumber);

        if(user != null) {
            model.addAttribute("user", user);
        }
        return "posts/posts-list";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        PostsResponseDto dto = postsService.findById(id);
        List<CommentsListResponseDto> commentsList = commentsService.findComments(id);

        model.addAttribute("post", dto);
        model.addAttribute("comments", commentsList);

        if(user != null) {
            model.addAttribute("user", user);
        }
        return "posts/posts-update";
    }

    @GetMapping("/test/api/main")
    public String testApiMain() {
        return "test/apiMain";
    }

    @GetMapping("/function/lotto")
    public String lottoMain(Model model, @LoginUser SessionUser user) {
        if(user != null) {
            model.addAttribute("user", user);
        }
        return "lotto/lottoMain";
    }

    @GetMapping("/function/bitcoin")
    public String bitcoinMain(Model model, @LoginUser SessionUser user) {
        model.addAttribute("wallet", bitcoinService.walletCreate(user));

        if(user != null) {
            model.addAttribute("user", user);
        }
        return "bitcoin/bitcoinMain";
    }
}
