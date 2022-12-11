package com.cocoballdiary.service;

import com.cocoballdiary.domain.Article;
import com.cocoballdiary.domain.Comment;
import com.cocoballdiary.domain.User;
import com.cocoballdiary.dto.CommentDto;
import com.cocoballdiary.dto.PageRequestDto;
import com.cocoballdiary.dto.PageResponseDto;
import com.cocoballdiary.exception.DiaryException;
import com.cocoballdiary.exception.ErrorCode;
import com.cocoballdiary.repository.ArticleRepository;
import com.cocoballdiary.repository.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;


    public PageResponseDto<CommentDto> getCommentList(Long aid, PageRequestDto pageRequestDto) {

        // 페이징 처리
        Pageable pageable = PageRequest.of(pageRequestDto.getPage() <= 0 ? 0 : pageRequestDto.getPage() - 1,
                pageRequestDto.getSize(),
                Sort.by("cid").ascending());

        Page<Comment> result = commentRepository.findByArticle_Aid(aid, pageable);

        List<CommentDto> dtoList =
                result.getContent().stream().map(CommentDto::from).collect(Collectors.toList());

        return PageResponseDto.<CommentDto>withAll()
                .pageRequestDto(pageRequestDto)
                .dtoList(dtoList)
                .total((int) result.getTotalElements())
                .build();
    }

    public CommentDto getComment(Long cid) {
        return commentRepository.findByCid(cid).map(CommentDto::from)
                                    .orElseThrow(() -> new DiaryException(ErrorCode.COMMENT_NOT_FOUND));

    }

    public Long writeComment(CommentDto commentDto) {

        Article article = articleRepository.getReferenceById(commentDto.getAid());
        User user = userRepository.getReferenceById(commentDto.getCommentUid());
        Long cid = commentRepository.save(commentDto.toEntity(user, article)).getCid();

        return cid;
    }

    public void modifyComment(CommentDto commentDto) {

        Comment comment = commentRepository.findByCid(commentDto.getCid())
                                    .orElseThrow(() -> new DiaryException(ErrorCode.COMMENT_NOT_FOUND));

        comment.setCommentDescription(commentDto.getCommentDescription());

        // TODO: 평점 수정 기능 추후 구현 필요

        commentRepository.save(comment);

    }

    public void deleteComment(Long cid) {
        commentRepository.deleteByCid(cid);
    }

}
