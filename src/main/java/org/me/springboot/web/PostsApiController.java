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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    /** 게시판의 게시글과 코멘트의 읽기, 생성, 수정, 삭제 등을 담당합니다.
     *
     * save() : 게시글을 저장합니다.
     * update() : 게시글을 수정합니다.
     * findById() : 게시글의 상세정보를 가져옵니다.
     * delete() : 게시글을 삭제합니다.
     *
     * saveComments() : 코멘트를 저장합니다.
     * updateComments() : 코멘트를 수정합니다.
     * deleteComments() : 코멘트를 삭제합니다.
     */
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
