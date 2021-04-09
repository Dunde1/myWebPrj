package org.me.springboot.web.dto.posts;

import lombok.Getter;
import org.me.springboot.domain.posts.Posts;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private int comments;

    private Posts posts;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.comments = entity.getComments();

        this.posts = entity;
    }
}
