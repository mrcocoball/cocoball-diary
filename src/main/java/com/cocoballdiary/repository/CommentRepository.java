package com.cocoballdiary.repository;

import com.cocoballdiary.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.article.aid = :aid")
    Page<Comment> findByArticle_Aid(@Param("aid")Long aid, Pageable pageable);

    Optional<Comment> findByCid(Long cid);

    void deleteByCid(Long cid);

    void deleteByArticle_Aid(Long aid);

}
