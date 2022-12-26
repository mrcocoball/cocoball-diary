package com.cocoballdiary.dto;

import com.cocoballdiary.domain.User;
import lombok.Data;

@Data
public class UserModifyDto {

    private String uid;
    private String password;
    private String email;
    private String introduce;

    private UserModifyDto(String uid, String password, String email, String introduce) {
        this.uid = uid;
        this.password = password;
        this.email = email;
        this.introduce = introduce;
    }

    // Entity -> Dto
    public static UserModifyDto from(User entity) {
        return new UserModifyDto(
                entity.getUid(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getIntroduce()
        );
    }

}
