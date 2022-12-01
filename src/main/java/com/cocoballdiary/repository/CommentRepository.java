package com.cocoballdiary.repository;

import com.cocoballdiary.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    void deleteByArticle_Aid(Long aid);

}
