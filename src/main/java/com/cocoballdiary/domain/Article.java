package com.cocoballdiary.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "createdAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;

    @Setter
    @Column(length = 50, nullable = false)
    private String title;

    @Setter
    @Column(length = 1000, nullable = false)
    private String description;

    @Setter
    @Column
    private Long score;

    @Setter
    @Column
    private Long count;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private User user;

    @ToString.Exclude
    @OneToMany(mappedBy = "article",
            cascade = {CascadeType.ALL},
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Builder.Default
    private Set<Image> images = new LinkedHashSet<>();


    protected Article() {
    }

    private Article(User user, String title, String description, Long score, Long count) {

        this.user = user;
        this.title = title;
        this.description = description;
        this.score = score;
        this.count = count;

    }

    public Article of(User user, String title, String description, Long score, Long count) {
        return new Article(user, title, description, score, count);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article that)) return false;
        return aid != null && aid.equals(that.getAid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(aid);
    }

}
