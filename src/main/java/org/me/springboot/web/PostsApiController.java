package org.me.springboot.web;

import lombok.RequiredArgsConstructor;
import org.me.springboot.domain.posts.Posts;
import org.me.springboot.domain.posts.comments.CommentsId;
import org.me.springboot.service.posts.PostsService;
import org.me.springboot.service.posts.comments.CommentsService;
import org.me.springboot.web.dto.posts.PostsResponseDto;
import org.me.springboot.web.dto.posts.PostsSaveRequestDto;
import org.me.springboot.web.dto.posts.PostsUpdateRequestDto;
import org.me.springboot.web.dto.posts.comments.CommentsSaveRequestDto;
import org.me.springboot.web.dto.posts.comments.CommentsUpdateRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;
    private final CommentsService commentsService;

    //포스트 라인
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id){
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    //코멘트 라인
    @PostMapping("/api/v1/posts/{id}/comments")
    public CommentsId saveComments(@RequestBody CommentsSaveRequestDto requestDto) {
        return commentsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}/comments/{step}")
    public CommentsId updateComments(@PathVariable Long id, @PathVariable Integer step, @RequestBody CommentsUpdateRequestDto requestDto){

        CommentsId commentsId = new CommentsId();
        Posts posts = findById(id).getPosts();

        commentsId.setPosts(posts);
        commentsId.setStep(step);

        return commentsService.update(commentsId, requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}/comments/{step}")
    public CommentsId deleteComments(@PathVariable Long id, @PathVariable Integer step){
        CommentsId commentsId = new CommentsId();
        Posts posts = findById(id).getPosts();

        commentsId.setPosts(posts);
        commentsId.setStep(step);

        commentsService.delete(commentsId);
        return commentsId;
    }
}
