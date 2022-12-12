package com.cocoballdiary.repository;

import com.cocoballdiary.dto.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleSearch {

    // TODO : ArticleWithImageDto로 변경 필요
    Page<ArticleDto> searchAll(String[] types, String keyword, Pageable pageable);

}
