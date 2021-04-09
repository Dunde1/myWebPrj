package org.me.springboot.domain.posts.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, CommentsId> {
    @Query(nativeQuery = true, value = "SELECT MAX(step) FROM comments WHERE post_id=:postId")
    Integer countMaxStep(@Param("postId") Long postId);

    @Query(nativeQuery = true, value = "SELECT * FROM comments WHERE post_id=:postId ORDER BY step")
    List<Comments> findComments(@Param("postId") Long postId);
}
