package org.me.springboot.domain.posts.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.me.springboot.domain.BaseTimeEntity;
import org.me.springboot.domain.posts.Posts;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comments extends BaseTimeEntity {

    @EmbeddedId
    private CommentsId commentsId;

    @Column(length = 100, nullable = false)
    private String comment;

    private String author;

    @Builder
    public Comments(CommentsId commentsId, String comment, String author) {
        this.commentsId = commentsId;
        this.comment = comment;
        this.author = author;
    }

    public void update(String comment) {
        this.comment = comment;
    }
}
