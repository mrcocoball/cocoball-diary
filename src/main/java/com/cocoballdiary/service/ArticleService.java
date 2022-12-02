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
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final UserRepository userRepository;


	public PageResponseDto<ArticleDto> getArticleList(PageRequestDto pageRequestDto) {

		// 페이징 처리
		Pageable pageable = PageRequest.of(pageRequestDto.getPage() <=0? 0 : pageRequestDto.getPage() -1,
				pageRequestDto.getSize(),
				Sort.by("aid").ascending());

		Page<Article> result = articleRepository.findAll(pageable);

		List<ArticleDto> dtoList =
			result.getContent().stream().map(ArticleDto::from).collect(Collectors.toList());

		return PageResponseDto.<ArticleDto>withAll()
				.pageRequestDto(pageRequestDto)
				.dtoList(dtoList)
				.total((int)result.getTotalElements())
				.build();
	}

	public Long createArticle(ArticleDto articleDto) {

		User user = userRepository.getReferenceById(articleDto.getUid());
		Long aid = articleRepository.save(articleDto.toEntity(user)).getAid();

		return aid;

	}

	public ArticleDto getArticle(Long aid) {
		return articleRepository.findByAid(aid).map(ArticleDto::from)
									.orElseThrow(() -> new DiaryException(ErrorCode.ARTICLE_NOT_FOUND));

	}

	public void deleteArticle(Long aid) {
		articleRepository.deleteByAid(aid);
	}

}
