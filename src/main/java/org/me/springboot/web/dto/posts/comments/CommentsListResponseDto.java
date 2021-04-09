package org.me.springboot.web.dto.posts.comments;

import lombok.Getter;
import org.me.springboot.domain.posts.Posts;
import org.me.springboot.domain.posts.comments.Comments;
import org.me.springboot.domain.posts.comments.CommentsId;

import java.time.LocalDateTime;

@Getter
public class CommentsListResponseDto {
    private int step;
    private String comment;
    private String author;
    private LocalDateTime modifiedDate;

    public CommentsListResponseDto(Comments entity){
        this.step = entity.getCommentsId().getStep();
        this.comment = entity.getComment();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}
