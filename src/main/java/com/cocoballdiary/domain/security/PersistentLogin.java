package com.cocoballdiary.domain.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "persistent_logins")
public class PersistentLogin {

    /**
     *  https://shirohoo.github.io/spring/spring-security/2021-10-08-remember-me/ 참고
     */

    @Id
    @Column(length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(nullable = false, length = 64)
    private Date lastUsed;
}
