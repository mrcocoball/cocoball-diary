package com.cocoballdiary.repository;

import com.cocoballdiary.dto.ArticleDto;
import com.cocoballdiary.dto.ArticleWithImageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleSearch {

    Page<ArticleWithImageDto> searchAll(String[] types, String keyword, Pageable pageable);

}
