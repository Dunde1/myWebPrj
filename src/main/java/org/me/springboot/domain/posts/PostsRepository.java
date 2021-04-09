package org.me.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM posts ORDER BY id DESC LIMIT :page, 10")  //테이블이름 X, Entity 이름 O //테이블칼럼이름도 Entity의 속성 or Entity 이름이 들어가야 함.
    List<Posts> findPageDesc(@Param("page") int page);    //쿼리에 받아 쓰려면 :page로 사용

    @Query("SELECT COUNT(p) FROM Posts p")
    int allRowCount();

    @Query(nativeQuery = true, value = "SELECT * FROM posts WHERE title Like :word ORDER BY id DESC LIMIT :page, 10")
    List<Posts> findTitlePageDesc(@Param("page") int page, @Param("word") String word);

    @Query("SELECT COUNT(p) FROM Posts p WHERE p.title LIKE :word")
    int allTitleRowCount(@Param("word") String word);

    @Query(nativeQuery = true, value = "SELECT * FROM posts WHERE author Like :word ORDER BY id DESC LIMIT :page, 10")
    List<Posts> findAuthorPageDesc(@Param("page") int page, @Param("word") String word);

    @Query("SELECT COUNT(p) FROM Posts p WHERE p.author LIKE :word")
    int allAuthorRowCount(@Param("word") String word);

}
