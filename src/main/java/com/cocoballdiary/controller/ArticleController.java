package com.cocoballdiary.controller;

import com.cocoballdiary.dto.ArticleDto;
import com.cocoballdiary.dto.ArticleWithImageDto;
import com.cocoballdiary.dto.PageRequestDto;
import com.cocoballdiary.dto.PageResponseDto;
import com.cocoballdiary.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Tag(name = "ArticleController")
@RequiredArgsConstructor
@RequestMapping("/diary")
@Log4j2
public class ArticleController {

    @Value("${com.cocoballdiary.upload.path}")
    private String uploadPath;

    private final ArticleService articleService;

    @Operation(summary = "Article List", description = "[GET] 게시글 리스트 조회")
    @GetMapping("/list")
    public void getArticleList(PageRequestDto pageRequestDto, Model model) {

        PageResponseDto<ArticleWithImageDto> responseDto = articleService.getArticleList(pageRequestDto);

        model.addAttribute("responseDto", responseDto);
    }

    @Operation(summary = "Article", description = "[GET] 게시글 단건 조회")
    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/read", "/modify"})
    public void getArticle(Long aid, PageRequestDto pageRequestDto, Model model) {

        ArticleDto articleDto = articleService.getArticleWithImage(aid);

        model.addAttribute("dto", articleDto);

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/write")
    public void writeGET() {
    }

    @Operation(summary = "Article POST", description = "[POST] 게시글 작성")
    @PostMapping("/write")
    public String writeArticle(@Valid ArticleDto articleDto,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        log.info(articleDto);

        if (bindingResult.hasErrors()) {
            log.error("has errors");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/diary/write";
        }

        Long aid = articleService.writeArticle(articleDto);

        log.info(aid);

        redirectAttributes.addFlashAttribute("result", aid);

        return "redirect:/diary/list";

    }

    @Operation(summary = "Article MODIFY", description = "[POST] 게시글 수정")
    @PreAuthorize("principal.username == #articleDto.uid")
    @PostMapping("/modify")
    public String modifyArticle(@Valid ArticleDto articleDto,
                                BindingResult bindingResult,
                                PageRequestDto pageRequestDto,
                                RedirectAttributes redirectAttributes) {

        log.info(articleDto);

        if (bindingResult.hasErrors()) {
            log.error("has errors");
            String link = pageRequestDto.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("aid", articleDto.getAid());

            return "redirect:/diary/modify?" + link;
        }

        articleService.modifyArticle(articleDto);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("aid", articleDto.getAid());

        return "redirect:/diary/read";

    }

    @Operation(summary = "Article DELETE", description = "[DELETE] 게시글 삭제")
    @PostMapping("/delete")
    public String deleteArticle(Long aid, RedirectAttributes redirectAttributes) {

        log.info("delete...");

        ArticleDto articleDto = articleService.getArticle(aid);
        articleService.deleteArticle(aid);

        // 첨부 파일 삭제 처리
        List<String> fileNames = articleDto.getFileNames();
        if (fileNames != null && fileNames.size() > 0) {
            removeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/diary/list";

    }

    public void removeFiles(List<String> files) {

        for (String fileName : files) {

            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();


            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();

                //섬네일이 존재한다면
                if (contentType.startsWith("image")) {
                    File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);
                    thumbnailFile.delete();
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }
    }

}
