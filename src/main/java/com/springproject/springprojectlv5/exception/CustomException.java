package com.springproject.springprojectlv5.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 에러 코드 정의
@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}

