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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private String placeName;

    @NotEmpty
    private String address;

    // 첨부파일 이름 리스트
    private List<String> fileNames;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime modifiedAt;

    private ArticleDto(String uid, Long aid, String title, String description, Long score, String placeName, String address, List<String> fileNames, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.uid = uid;
        this.aid = aid;
        this.title = title;
        this.description = description;
        this.score = score;
        this.placeName = placeName;
        this.address = address;
        this.fileNames = fileNames;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ArticleDto of(String uid, Long aid, String title, String description, Long score, String placeName, String address, List<String> filenames, LocalDateTime createdAt, LocalDateTime modifiedAt) {

        if (score == null) {
            score = 0L;
        }

        return new ArticleDto(uid, aid, title, description, score, placeName, address, filenames, createdAt, modifiedAt);
    }

    // Entity -> Dto
    public static ArticleDto from(Article entity) {

        List<String> fileNames = entity.getImages()
                .stream()
                .sorted()
                .map(image -> image.getUuid() + "_" + image.getFileName())
                .collect(Collectors.toList());

        return new ArticleDto(
                entity.getUser().getUid(),
                entity.getAid(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getScore(),
                entity.getPlaceName(),
                entity.getAddress(),
                fileNames,
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

        Article article = Article.of(
                user,
                title,
                description,
                score,
                placeName,
                address
        );

        List<String> fileNames = this.fileNames;

        if (fileNames != null) {
            fileNames.forEach(fileName -> {
                String[] arr = fileName.split("_");
                article.addImage(arr[0], arr[1]);
            });
        }

        return article;
    }
}
