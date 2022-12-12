package com.cocoballdiary.repository;

import com.cocoballdiary.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleSearch {

    Page<Article> findAll(Pageable pageable);

    Optional<Article> findByAid(Long aid);

    void deleteByAid(Long aid);

    void deleteByUser_Uid(String uid);

}
