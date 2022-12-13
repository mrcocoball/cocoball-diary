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

    private int ord;

    @ManyToOne
    private Article article;

	private Image(Article article, String uuid, String fileName, int ord) {
		this.article = article;
		this.uuid = uuid;
		this.fileName = fileName;
		this.ord = ord;
	}

	public static Image of (Article article, String uuid, String fileName, int ord) {

		return new Image(article, uuid, fileName, ord);

	}

    @Override
    public int compareTo(Image other) {
        return this.ord - other.ord;
    }

	public void changeArticle(Article article) {
		this.article = null;

	}

}
