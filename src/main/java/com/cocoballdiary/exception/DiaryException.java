package com.cocoballdiary.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiaryException extends RuntimeException {
    private ErrorCode errorCode;
    private String detail;

    public DiaryException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.detail = null;
    }

    @Override
    public String getMessage() {
        if (detail == null) {
            return errorCode.getMessage();
        }

        return String.format("%s. %s", errorCode.getMessage(), detail);
    }
}
