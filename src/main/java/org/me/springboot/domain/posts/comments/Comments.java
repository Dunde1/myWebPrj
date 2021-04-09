package org.me.springboot.domain.posts.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.me.springboot.domain.BaseTimeEntity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

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
    public Comments(String comment, String author) {
        this.comment = comment;
        this.author = author;
    }
}
