package com.cocoballdiary.controller;

import com.cocoballdiary.dto.CommentDto;
import com.cocoballdiary.dto.PageRequestDto;
import com.cocoballdiary.dto.PageResponseDto;
import com.cocoballdiary.service.CommentService;
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

    @Operation(summary = "Comment POST", description = "[POST] 덧글 작성")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> writeComment(@Valid @RequestBody CommentDto commentDto,
                                          BindingResult bindingResult) throws BindException {

        log.info(commentDto);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();

        Long cid = commentService.writeComment(commentDto);

        resultMap.put("cid", cid);

        return resultMap;

    }

    @Operation(summary = "Comment MODIFY", description = "[PUT] 덧글 수정")
    @PreAuthorize("principal.username == #commentDto.uid")
    @PutMapping(value = "/{cid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modifyComment(@PathVariable("cid") Long cid, @RequestBody CommentDto commentDto) {

        commentDto.setCid(cid); // 번호 일치

        commentService.modifyComment(commentDto);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("cid", cid);

        return resultMap;

    }

    @Operation(summary = "Comment DELETE", description = "[DELETE] 덧글 삭제")
    @PreAuthorize("principal.username == #commentDto.uid")
    @DeleteMapping("/{cid}")
    public Map<String, Long> deleteComment(@PathVariable("cid") Long cid) {

        commentService.deleteComment(cid);

        Map<String, Long> resultMap = new HashMap<>();

        resultMap.put("cid", cid);

        return resultMap;
    }

}
