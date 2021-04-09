package org.me.springboot.domain.posts.comments;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.me.springboot.domain.posts.Posts;
import org.me.springboot.domain.posts.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommentsRepositoryTest {

    @Autowired
    CommentsRepository commentsRepository;

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
        //commentsRepository.deleteAll();
    }

    @Test
    public void 코멘트저장_불러오기() {
        //given
        List<Posts> list = postsRepository.findPageDesc(1);
        Posts posts = list.get(0);

        Integer step = commentsRepository.countMaxStep(posts.getId());
        step = step==null?0:step+1;

        CommentsId commentsId = new CommentsId();
        commentsId.setPosts(posts);
        commentsId.setStep(step);

        String comment = "테스트 코멘트";
        String author = "테스터";

        System.out.println("확인용"+posts.getId());

        commentsRepository.save(Comments.builder().commentsId(commentsId).comment(comment).author(author).build());

        //when
        List<Comments> commentsList = commentsRepository.findAll();

        //then
        Comments comments = commentsList.get(0);
        assertThat(comments.getComment()).isEqualTo(comment);
        assertThat(comments.getAuthor()).isEqualTo(author);
    }
}
