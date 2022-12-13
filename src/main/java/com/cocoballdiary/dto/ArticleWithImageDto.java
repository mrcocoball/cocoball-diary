package com.cocoballdiary.dto;

import com.cocoballdiary.domain.Article;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class ArticleWithImageDto {

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

    private List<ImageDto> images;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime modifiedAt;

    private ArticleWithImageDto(String uid, Long aid, String title, String description, Long score, String placeName, String address, List<ImageDto> images, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.uid = uid;
        this.aid = aid;
        this.title = title;
        this.description = description;
        this.score = score;
        this.placeName = placeName;
        this.address = address;
        this.images = images;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static ArticleWithImageDto of(String uid, Long aid, String title, String description, Long score, String placeName, String address, List<ImageDto> images, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new ArticleWithImageDto(uid, aid, title, description, score, placeName, address, images, createdAt, modifiedAt);
    }

    // Entity -> Dto
    public static ArticleWithImageDto from(Article entity) {

        return new ArticleWithImageDto(
                entity.getUser().getUid(),
                entity.getAid(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getScore(),
                entity.getPlaceName(),
                entity.getAddress(),
                entity.getImages().stream().map(ImageDto::from).collect(Collectors.toList()),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
}
