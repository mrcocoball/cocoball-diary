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
        @Index(columnList = "createdAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comment extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    @Setter
    @Column(length = 1000, nullable = false)
    private String description;

    @Setter
    @Column(nullable = false)
    private Long score;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aid")
    private Article article;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;


    protected Comment() {
    }

    private Comment(User user, Article article, String description, Long score) {

        this.user = user;
        this.article = article;
        this.description = description;
        this.score = score;

    }

    public static Comment of(User user, Article article, String description, Long score) {
        return new Comment(user, article, description, score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment that)) return false;
        return cid != null && cid.equals(that.getCid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid);
    }

}
