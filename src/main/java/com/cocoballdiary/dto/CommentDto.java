package com.cocoballdiary.dto;

import com.cocoballdiary.domain.Article;
import com.cocoballdiary.domain.Comment;
import com.cocoballdiary.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Setter
@Getter
public class CommentDto {

    @NotNull
    private String commentUid;

    private Long aid;

    private Long cid;

    @NotEmpty
    @Size(max=100)
    private String commentDescription;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime modifiedAt;

    public CommentDto() {}

    private CommentDto(String commentUid, Long aid, Long cid, String commentDescription, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.commentUid = commentUid;
        this.aid = aid;
        this.cid = cid;
        this.commentDescription = commentDescription;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static CommentDto of(String commentUid, Long aid, Long cid, String commentDescription, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new CommentDto(commentUid, aid, cid, commentDescription, createdAt, modifiedAt);
    }

    public static CommentDto initialize(String uid, Long aid, String description) {
        return new CommentDto(uid, aid, null, description, null, null);
    }

    public static CommentDto from(Comment entity) {

        return new CommentDto(
                entity.getUser().getUid(),
                entity.getArticle().getAid(),
                entity.getCid(),
                entity.getCommentDescription(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    public Comment toEntity(User user, Article article) {
        return Comment.of(
                user,
                article,
                commentDescription
        );
    }
}
