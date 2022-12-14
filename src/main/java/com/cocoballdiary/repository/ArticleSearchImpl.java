package com.cocoballdiary.repository;

import com.cocoballdiary.domain.Article;
import com.cocoballdiary.domain.QArticle;
import com.cocoballdiary.dto.ArticleDto;
import com.cocoballdiary.dto.ArticleWithImageDto;
import com.cocoballdiary.dto.ImageDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class ArticleSearchImpl extends QuerydslRepositorySupport implements ArticleSearch {

    public ArticleSearchImpl() {

        super(Article.class);

    }

    @Override
    public Page<ArticleWithImageDto> searchAll(String[] types, String keyword, Pageable pageable) {

        QArticle article = QArticle.article;
        JPQLQuery<Article> articleJPQLQuery = from(article);

        // 타입이 존재하고 검색어가 존재할 경우

        if ((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {

                switch (type) {

                    case "t":
                        booleanBuilder.or(article.title.contains(keyword));
                        break;
                    case "d":
                        booleanBuilder.or(article.description.contains(keyword));
                        break;
                    case "u":
                        booleanBuilder.or(article.user.uid.contains(keyword));
                        break;

                }

            }
            articleJPQLQuery.where(booleanBuilder);
        }
        articleJPQLQuery.groupBy(article);

        getQuerydsl().applyPagination(pageable, articleJPQLQuery); // paging

        // Dto List 변환

        List<Article> articleList = articleJPQLQuery.select(article).fetch();

        List<ArticleWithImageDto> dtoList = articleList.stream().map(article1 -> {

            ArticleWithImageDto dto = ArticleWithImageDto.from(article1);

            // 이미지 처리

            List<ImageDto> imageDtos = article1.getImages().stream()
                    .sorted()
                    .map(ImageDto::from)
                    .collect(Collectors.toList());

            dto.setImages(imageDtos);

            return dto;

        }).collect(Collectors.toList());

        long totalCount = articleJPQLQuery.fetchCount();
        return new PageImpl<>(dtoList, pageable, totalCount);
    }
}
