package org.me.springboot.domain.posts.comments;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.me.springboot.domain.posts.Posts;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
public class CommentsId implements Serializable {

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "postId")
    private Posts posts;

    @Column(name = "step")
    private int step;
}
