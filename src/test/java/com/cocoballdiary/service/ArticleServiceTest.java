package com.cocoballdiary.service;

import com.cocoballdiary.dto.ArticleDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
@DisplayName("비즈니스 로직 - 게시글")
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Test
    @DisplayName("INSERT 테스트")
    public void testCreateArticle() {

        ArticleDto articleDto = ArticleDto.initialize(
                "test1",
                "테스트용",
                "테스트용 내용",
                5L,
                1L
        );

        log.info(articleService.writeArticle(articleDto));

    }

}