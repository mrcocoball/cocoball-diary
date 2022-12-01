package com.cocoballdiary.repository;

import com.cocoballdiary.config.JpaConfig;
import com.cocoballdiary.domain.Article;
import com.cocoballdiary.domain.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    // private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    // private final CommentRepository commentRepository;

    public JpaRepositoryTest(
            //@Autowired UserRepository userRepository,
            @Autowired ArticleRepository articleRepository
            //@Autowired CommentRepository commentRepository
    ) {
        //this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        //this.commentRepository = commentRepository;
    }


    // TODO : MockData 추가 후 테스트 진행
    @Disabled
    @DisplayName("SELECT 테스트")
    @Test
    void selectTest() {
        //Given


        //When


        //Then

    }

    @DisplayName("INSERT 테스트 - 게시글")
    @Test
    void insertTest() {

        //Given
        long previous = articleRepository.count();
        User user = User.of("test", "test", "test", false, false);
        Article article = Article.of(user, "test", "description", 5L, 1L, 5L);

        //When
        articleRepository.save(article);

        //Then
        assertThat(articleRepository.count()).isEqualTo(previous + 1);

    }

    @DisplayName("UPDATE 테스트 - 게시글")
    @Test
    void updateTest() {

        //Given
        Article article = articleRepository.findByAid(1L).orElseThrow();
        String updatedTitle = "updated";
        article.setTitle(updatedTitle);

        //When
        Article updatedArticle = articleRepository.saveAndFlush(article);

        //Then
        assertThat(updatedArticle).hasFieldOrPropertyWithValue("title", updatedTitle);

    }

    @DisplayName("DELETE 테스트 - 게시글")
    @Test
    void deleteTest() {

        //Given
        Article article = articleRepository.findByAid(1L).orElseThrow();
        long previous = articleRepository.count();

        //When
        articleRepository.delete(article);

        //Then
        assertThat(articleRepository.count()).isEqualTo(previous - 1);

    }



    /*
    @EnableJpaAuditing
    @TestConfiguration
    static class TestJpaConfig {

        @Bean
        AuditorAware<String> auditorAware() {
            return () -> Optional.of("testuser");
        }

    }
     */

}
