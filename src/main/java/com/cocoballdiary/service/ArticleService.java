package com.cocoballdiary.service;

import com.cocoballdiary.domain.Article;
import com.cocoballdiary.domain.User;
import com.cocoballdiary.dto.ArticleDto;
import com.cocoballdiary.dto.PageRequestDto;
import com.cocoballdiary.dto.PageResponseDto;
import com.cocoballdiary.exception.DiaryException;
import com.cocoballdiary.exception.ErrorCode;
import com.cocoballdiary.repository.ArticleRepository;
import com.cocoballdiary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;


	/*
	public PageResponseDto<ArticleDto> getArticleList(PageRequestDto pageRequestDto) {

		// 페이징 처리
		Pageable pageable = PageRequest.of(pageRequestDto.getPage() <=0? 0 : pageRequestDto.getPage() -1,
				pageRequestDto.getSize(),
				Sort.by("aid").descending());

		Page<Article> result = articleRepository.findAll(pageable);

		List<ArticleDto> dtoList =
			result.getContent().stream().map(ArticleDto::from).collect(Collectors.toList());

		return PageResponseDto.<ArticleDto>withAll()
				.pageRequestDto(pageRequestDto)
				.dtoList(dtoList)
				.total((int)result.getTotalElements())
				.build();
	}
	 */

	public PageResponseDto<ArticleDto> getArticleList(PageRequestDto pageRequestDto) {

		String[] types = pageRequestDto.getTypes(); // title, description, uid
		String keyword = pageRequestDto.getKeyword();
		Pageable pageable = pageRequestDto.getPageable("aid");

		Page<ArticleDto> result = articleRepository.searchAll(types, keyword, pageable);

		return PageResponseDto.<ArticleDto>withAll()
				.pageRequestDto(pageRequestDto)
				.dtoList(result.getContent())
				.total((int)result.getTotalElements())
				.build();
	}

	public ArticleDto getArticle(Long aid) {
		return articleRepository.findByAid(aid).map(ArticleDto::from)
									.orElseThrow(() -> new DiaryException(ErrorCode.ARTICLE_NOT_FOUND));

	}

	public Long writeArticle(ArticleDto articleDto) {

		log.info(articleDto.getScore());

		User user = userRepository.getReferenceById(articleDto.getUid());
		Long aid = articleRepository.save(articleDto.toEntity(user)).getAid();

		return aid;

	}

	public void modifyArticle(ArticleDto articleDto) {

		Article article = articleRepository.findByAid(articleDto.getAid())
									.orElseThrow(() -> new DiaryException(ErrorCode.ARTICLE_NOT_FOUND));

		article.setTitle(articleDto.getTitle());
		article.setDescription(articleDto.getDescription());
		article.setScore(articleDto.getScore());

		// TODO: 평점 수정, 장소 지도 정보 수정 기능 추후 구현 필요

		articleRepository.save(article);
	}

	public void deleteArticle(Long aid) {
		articleRepository.deleteByAid(aid);
	}

}
