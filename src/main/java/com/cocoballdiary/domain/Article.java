package com.cocoballdiary.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
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
    private String placeName;

    @Setter
    @Column
    private String address;

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
    @BatchSize(size = 20)
    private Set<Image> images = new HashSet<>();


    protected Article() {
    }

    private Article(User user, String title, String description, Long score, String placeName, String address) {

        this.user = user;
        this.title = title;
        this.description = description;
        this.score = score;
        this.placeName = placeName;
        this.address = address;
    }

    public static Article of(User user, String title, String description, Long score, String placeName, String address) {
        return new Article(user, title, description, score, placeName, address);
    }

    public void addImage(String uuid, String fileName) {

   		Image image = Image.builder()
   						.uuid(uuid)
   						.fileName(fileName)
   						.article(this)
   						.ord(images.size())
   						.build();
   		images.add(image);
   	}


   	public void clearImages() {

   		images.forEach(image -> image.changeArticle(null));

   		this.images.clear();

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
