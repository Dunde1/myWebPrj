package org.me.springboot.domain.posts.comments;

import lombok.Data;
import org.me.springboot.domain.posts.Posts;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
public class CommentsId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "postId")
    private Posts posts;

    @Column(name = "step")
    private int step;
}
