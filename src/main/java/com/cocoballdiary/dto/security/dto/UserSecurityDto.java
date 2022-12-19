package com.cocoballdiary.dto.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
@ToString
public class UserSecurityDto extends User implements OAuth2User {

    private String uid;
    private String password;
    private String email;
    private boolean deleted;
    private boolean social;

    private Map<String, Object> props; // 소셜 로그인 정보

    public UserSecurityDto(String username, String password, String email, boolean deleted, boolean social,
                           Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);

        this.uid = username;
        this.password = password;
        this.email = email;
        this.deleted = deleted;
        this.social = social;
    }

    public Map<String, Object> getAttributes() {
        return this.getProps();
    }

    @Override
    public String getName() {
        return this.uid;
    }

}
