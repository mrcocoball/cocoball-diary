package com.cocoballdiary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    OK(HttpStatus.OK, "ok"),

    // 400 BAD_REQUEST : 잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),


    // 404 NOT_FOUND : Resource 를 찾을 수 없음
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "덧글을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),

    // 409 CONFICT : Resource의 현재 상태와 충돌, 보통 중복된 데이터 존재
    USER_EXISTS(HttpStatus.CONFLICT, "중복된 유저가 있습니다."),

    // 500 INTERNAL_SERVER_ERROR : 서버 내부 오류, 발견되지 않은 비즈니스 로직 상 문제나 Runtime Exception
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    public String getMessage(Throwable e) {
        return this.getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    private HttpStatus httpStatus;
    private String message;

}
