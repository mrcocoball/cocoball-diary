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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/read", "/modify"})
    public ArticleDto getArticle(Long aid) {

        ArticleDto articleDto = articleService.getArticle(aid);

        return articleDto;

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/write")
    public void writeGET() {}

    @Operation(summary = "Article POST", description = "[POST] 게시글 작성")
    @PostMapping(value = "/write", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> writeArticle(@Valid @RequestBody ArticleDto articleDto,
                                          BindingResult bindingResult) throws BindException {

        log.info(articleDto);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();

        Long aid = articleService.writeArticle(articleDto);

        resultMap.put("aid", aid);

        return resultMap;

    }

    @Operation(summary = "Article MODIFY", description = "[PUT] 게시글 수정")
    @PreAuthorize("principal.username == #articleDto.uid")
    @PutMapping(value = "/{aid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modifyArticle(@PathVariable("aid") Long aid, @RequestBody ArticleDto articleDto) {

        articleDto.setAid(aid); // 번호 일치

        articleService.modifyArticle(articleDto);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("aid", aid);

        return resultMap;

    }

    @Operation(summary = "Article DELETE", description = "[DELETE] 게시글 삭제")
    @PreAuthorize("principal.username == #articleDto.uid")
    @DeleteMapping("/{aid}")
    public Map<String, Long> deleteArticle(@PathVariable("aid") Long aid) {

        articleService.deleteArticle(aid);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("aid", aid);

        return resultMap;

    }

}
