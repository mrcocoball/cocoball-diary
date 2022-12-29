package com.cocoballdiary.dto;

import com.cocoballdiary.domain.User;
import lombok.Data;

@Data
public class UserJoinDto {

    private String uid;
    private String password;
    private String email;
    private String introduce;
    private boolean deleted;
    private boolean social;

    // Dto -> Entity
    public User toEntity() {
        return User.of(
                uid,
                password,
                email,
                introduce,
                false,
                false
        );
    }
}
