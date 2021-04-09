package org.me.springboot.domain.posts.comments;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Data
@Embeddable
public class CommentsId implements Serializable {

    @Column(name = "id")
    private Long post_id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int step;
}
