package com.cocoballdiary.controller;

import com.cocoballdiary.dto.CommentDto;
import com.cocoballdiary.dto.PageRequestDto;
import com.cocoballdiary.dto.PageResponseDto;
import com.cocoballdiary.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "CommentController")
@RequiredArgsConstructor
@RequestMapping("/comments")
@Log4j2
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Comment List", description = "[GET] 게시글 내 덧글 리스트 조회")
    @GetMapping(value = "/list/{aid}")
    public PageResponseDto<CommentDto> getCommentList(@PathVariable("aid") Long aid,
                                                      PageRequestDto pageRequestDto) {

        PageResponseDto<CommentDto> responseDto = commentService.getCommentList(aid, pageRequestDto);

        return responseDto;
    }

    @Operation(summary = "Comment", description = "[GET] 덧글 단건 조회")
    @GetMapping("/{cid}")
    public CommentDto getComment(@PathVariable("cid") Long cid) {

        CommentDto commentDto = commentService.getComment(cid);

        return commentDto;

    }

}
