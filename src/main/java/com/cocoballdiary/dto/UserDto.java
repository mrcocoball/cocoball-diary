package com.cocoballdiary.dto;

import com.cocoballdiary.domain.Article;
import com.cocoballdiary.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Setter
@Getter
public class UserDto {

    @NotEmpty
    private String uid;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;

    private String introduce;

    private boolean deleted;

    private boolean social;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime modifiedAt;

    private UserDto(String uid, String password, String email, String introduce, boolean deleted, boolean social, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.uid = uid;
        this.password = password;
        this.email = email;
        this.introduce = introduce;
        this.deleted = deleted;
        this.social = social;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static UserDto of(String uid, String password, String email, String introduce, boolean deleted, boolean social, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        return new UserDto(uid, password, email, introduce, deleted, social, createdAt, modifiedAt);
    }

    // Entity -> Dto
    public static UserDto from(User entity) {
        return new UserDto(
                entity.getUid(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getIntroduce(),
                entity.isDeleted(),
                entity.isSocial(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

}
