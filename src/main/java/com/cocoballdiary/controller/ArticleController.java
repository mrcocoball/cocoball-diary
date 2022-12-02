package com.cocoballdiary.controller;

import com.cocoballdiary.dto.ArticleDto;
import com.cocoballdiary.dto.PageRequestDto;
import com.cocoballdiary.dto.PageResponseDto;
import com.cocoballdiary.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "ArticleController")
@RequiredArgsConstructor
@RequestMapping("/diary")
@Log4j2
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "Article List", description = "[GET] 게시글 리스트 조회")
    @GetMapping("/list")
    public PageResponseDto<ArticleDto> getArticleList(PageRequestDto pageRequestDto) {

        PageResponseDto<ArticleDto> responseDto = articleService.getArticleList(pageRequestDto);

        return responseDto;
    }

    @Operation(summary = "Article", description = "[GET] 게시글 단건 조회")
    @GetMapping({"/read", "/modify"})
    public ArticleDto getArticle(Long aid) {

        ArticleDto articleDto = articleService.getArticle(aid);

        return articleDto;

    }

    @Operation(summary = "Article POST", description = "[POST] 게시글 작성")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> createArticle(@Valid @RequestBody ArticleDto articleDto,
                                           BindingResult bindingResult) throws BindException {

        log.info(articleDto);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();

        Long aid = articleService.createArticle(articleDto);

        resultMap.put("aid", aid);

        return resultMap;

    }

    @Operation(summary = "Article DELETE", description = "[DELETE] 게시글 삭제")
    @DeleteMapping("/{aid}")
    public Map<String, Long> deleteArticle(@PathVariable("aid") Long aid) {

        articleService.deleteArticle(aid);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("aid", aid);

        return resultMap;

    }

}
