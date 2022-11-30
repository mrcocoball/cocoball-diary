package com.cocoballdiary.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "article")
public class Image implements Comparable<Image> {

    @Id
    private String uuid;

    private String fileName;

    @Column
    private int ord;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @Override
    public int compareTo(Image other) {
        return this.ord - other.ord;
    }

}
