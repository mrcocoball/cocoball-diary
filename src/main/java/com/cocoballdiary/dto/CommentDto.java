package com.cocoballdiary.dto;

import com.cocoballdiary.domain.Article;
import com.cocoballdiary.domain.Comment;
import com.cocoballdiary.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
public class CommentDto {

    @NotNull
    private String commentUid;

    // @NotNull
    private Long aid;

    private Long cid;

    @NotEmpty
    private String commentDescription;

    @Max(5)
    // @NotNull
    private Long score;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime modifiedAt;

    private CommentDto(String commentUid, Long aid, Long cid, String commentDescription, Long score, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.commentUid = commentUid;
        this.aid = aid;
        this.cid = cid;
        this.commentDescription = commentDescription;
        this.score = score;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentDto of(String commentUid, Long aid, Long cid, String commentDescription, Long score, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new CommentDto(commentUid, aid, cid, commentDescription, score, createdAt, modifiedAt);
    }

    // Request를 통한 생성 시
    public static CommentDto initialize(String uid, Long aid, String description, Long score) {
        return new CommentDto(uid, aid, null, description, score, null, null);
    }

    // Entity -> Dto
    public static CommentDto from(Comment entity) {

        return new CommentDto(
                entity.getUser().getUid(),
                entity.getArticle().getAid(),
                entity.getCid(),
                entity.getCommentDescription(),
                entity.getScore(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    // Dto -> Entity
    public Comment toEntity(User user, Article article) {
        return Comment.of(
                user,
                article,
                commentDescription,
                score
        );
    }
}
