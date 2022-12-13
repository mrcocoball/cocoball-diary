package com.cocoballdiary.repository;

import com.cocoballdiary.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleSearch {

    Page<Article> findAll(Pageable pageable);

    Optional<Article> findByAid(Long aid);

    void deleteByAid(Long aid);

    void deleteByUser_Uid(String uid);

    @EntityGraph(attributePaths = {"images"})
    @Query("select a from Article a where a.aid =:aid")
    Optional<Article> findByIdWithImages(@Param("aid") Long aid);

}
