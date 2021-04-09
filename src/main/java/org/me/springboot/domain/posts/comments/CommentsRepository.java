package org.me.springboot.domain.posts.comments;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, CommentsId> {

}
