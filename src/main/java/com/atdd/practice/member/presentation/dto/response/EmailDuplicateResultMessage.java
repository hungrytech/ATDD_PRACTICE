package com.atdd.practice.member.presentation.dto.response;

import lombok.Getter;

@Getter
public enum EmailDuplicateResultMessage {
    DUPLICATE("이미 존재하는 아이디(이메일)입니다."),
    AVAILABLE("사용 가능한 아이디(이메일)입니다."),;

    private final String message;

    EmailDuplicateResultMessage(String message) {
        this.message = message;
    }
}
