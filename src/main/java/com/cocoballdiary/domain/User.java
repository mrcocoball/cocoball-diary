package com.cocoballdiary.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "email"),
        @Index(columnList = "createdAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User extends AuditingFields {

    @Id
    @Column(length = 50)
    private String uid;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(length = 50, nullable = false)
    private String email;

    @Setter
    @Column
    private boolean deleted;

    @Setter
    @Column
    private boolean social;


    protected User() {
    }

    private User(String uid, String password, String email, boolean deleted, boolean social) {

        this.uid = uid;
        this.password = password;
        this.email = email;
        this.deleted = deleted;
        this.social = social;
    }

    public static User of(String uid, String password, String email, boolean deleted, boolean social) {
        return new User(uid, password, email, deleted, social);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User that)) return false;
        return uid != null && uid.equals(that.getUid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

}
