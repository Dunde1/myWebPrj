package org.me.springboot.web.dto.posts.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.me.springboot.domain.posts.comments.Comments;
import org.me.springboot.domain.posts.comments.CommentsId;

@Getter
@NoArgsConstructor
public class CommentsSaveRequestDto {
    private CommentsId commentsId;
    private String comment;
    private String author;

    @Builder
    public CommentsSaveRequestDto(CommentsId commentsId, String comment, String author) {
        this.commentsId = commentsId;
        this.comment = comment;
        this.author = author;
    }

    public Comments toEntity() {
        return Comments.builder()
                .commentsId(commentsId)
                .comment(comment)
                .author(author)
                .build();
    }
}
