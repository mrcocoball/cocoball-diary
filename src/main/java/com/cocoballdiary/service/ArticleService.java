package com.cocoballdiary.service;

import com.cocoballdiary.domain.Article;
import com.cocoballdiary.domain.User;
import com.cocoballdiary.dto.ArticleDto;
import com.cocoballdiary.dto.ArticleWithImageDto;
import com.cocoballdiary.dto.PageRequestDto;
import com.cocoballdiary.dto.PageResponseDto;
import com.cocoballdiary.exception.DiaryException;
import com.cocoballdiary.exception.ErrorCode;
import com.cocoballdiary.repository.ArticleRepository;
import com.cocoballdiary.repository.CommentRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public PageResponseDto<ArticleWithImageDto> getArticleList(PageRequestDto pageRequestDto) {

        String[] types = pageRequestDto.getTypes(); // title, description, uid
        String keyword = pageRequestDto.getKeyword();
        Pageable pageable = pageRequestDto.getPageable("aid");

        Page<ArticleWithImageDto> result = articleRepository.searchAll(types, keyword, pageable);

        return PageResponseDto.<ArticleWithImageDto>withAll()
                .pageRequestDto(pageRequestDto)
                .dtoList(result.getContent())
                .total((int) result.getTotalElements())
                .build();
    }

    public ArticleDto getArticle(Long aid) {
        return articleRepository.findByAid(aid).map(ArticleDto::from)
                .orElseThrow(() -> new DiaryException(ErrorCode.ARTICLE_NOT_FOUND));

    }

    public ArticleDto getArticleWithImage(Long aid) {

        return articleRepository.findByIdWithImages(aid).map(ArticleDto::from)
                .orElseThrow(() -> new DiaryException(ErrorCode.USER_NOT_FOUND));
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
        article.setPlaceName(articleDto.getPlaceName());
        article.setAddress(articleDto.getAddress());

        // 첨부파일 처리
        article.clearImages();

        if(articleDto.getFileNames() != null) {
            for (String fileName : articleDto.getFileNames()) {
                String[] arr = fileName.split("_");
                article.addImage(arr[0], arr[1]);
            }
        }

        articleRepository.save(article);
    }

    public void deleteArticle(Long aid) {
        commentRepository.deleteByArticle_Aid(aid);
        articleRepository.deleteByAid(aid);
    }

}
