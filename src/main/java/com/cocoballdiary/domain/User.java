package com.cocoballdiary.domain;

import com.cocoballdiary.domain.constrant.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true, exclude = "roleSet")
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

    @Column(nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @Column
    private boolean deleted;

    @Column
    private boolean social;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRole> roleSet = new HashSet<>();


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

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeDelete(boolean deleted) {
        this.deleted = deleted;
    }

    public void addRole(UserRole userRole) {
        this.roleSet.add(userRole);
    }

    public void clearRoles() {
        this.roleSet.clear();
    }

    public void changeSocial(boolean social) {
        this.social = social;
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
