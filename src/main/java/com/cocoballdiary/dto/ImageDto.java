package com.cocoballdiary.dto;

import com.cocoballdiary.domain.Article;
import com.cocoballdiary.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

	private String uuid;
	private String fileName;
	private int ord;


	public static ImageDto of(String uuid, String fileName, int ord) {

		return new ImageDto(uuid, fileName, ord);

	}

	// Entity -> Dto
	public static ImageDto from(Image entity) {

		return new ImageDto(
				entity.getUuid(),
				entity.getFileName(),
				entity.getOrd()
		);
	}
}
