package com.cocoballdiary.dto;

import com.cocoballdiary.domain.Article;
import com.cocoballdiary.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
public class ArticleDto {

    @NotNull
    private String uid;

    private Long aid;

    @NotEmpty
    private String title;

    @NotEmpty
    private String description;

    @Max(5)
    @NotNull
    private Long score;

    private Long count;

    private Long totalScore;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime modifiedAt;

    private ArticleDto(String uid, Long aid, String title, String description, Long score, Long count, Long totalScore, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.uid = uid;
        this.aid = aid;
        this.title = title;
        this.description = description;
        this.score = score;
        this.count = count;
        this.totalScore = totalScore;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ArticleDto of(String uid, Long aid, String title, String description, Long score, Long count, Long totalScore, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new ArticleDto(uid, aid, title, description, score, count, totalScore, createdAt, modifiedAt);
    }

    // Request를 통한 생성 시
    public static ArticleDto initialize(String uid, String title, String description, Long score, Long count) {

        Long totalScore = score;

        return new ArticleDto(uid, null, title, description, score, count, totalScore, null, null);
    }

    // Entity -> Dto
    public static ArticleDto from(Article entity) {

        return new ArticleDto(
                entity.getUser().getUid(),
                entity.getAid(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getScore(),
                entity.getCount(),
                entity.getTotalScore(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    // Dto -> Entity
    public Article toEntity(User user) {
        return Article.of(
                user,
                title,
                description,
                score,
                count,
                totalScore
        );
    }
}
