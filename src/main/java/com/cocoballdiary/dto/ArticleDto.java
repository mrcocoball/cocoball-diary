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
    private Long score;

    @NotEmpty
    private String placename;

    @NotEmpty
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime modifiedAt;

    private ArticleDto(String uid, Long aid, String title, String description, Long score, String placename, String address, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.uid = uid;
        this.aid = aid;
        this.title = title;
        this.description = description;
        this.score = score;
        this.placename = placename;
        this.address = address;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ArticleDto of(String uid, Long aid, String title, String description, Long score, String placename, String address, LocalDateTime createdAt, LocalDateTime modifiedAt) {

        if (score == null) {
            score = 0L;
        }

        return new ArticleDto(uid, aid, title, description, score, placename, address, createdAt, modifiedAt);
    }

    // Entity -> Dto
    public static ArticleDto from(Article entity) {

        return new ArticleDto(
                entity.getUser().getUid(),
                entity.getAid(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getScore(),
                entity.getPlacename(),
                entity.getAddress(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    // Dto -> Entity
    public Article toEntity(User user) {

        Long score = this.score;

        if (score == null) {
            score = 0L;
        }

        return Article.of(
                user,
                title,
                description,
                score,
                placename,
                address
        );
    }
}
