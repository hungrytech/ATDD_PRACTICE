package com.atdd.practice.member.domain;

import com.atdd.practice.member.application.exception.InvalidEmailException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9+-_]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");

    @Column(name = "email", nullable = false, unique = true)
    private String value;

    private Email(String email) {
        this.value = email;
    }

    public static Email of(String email) {
        validateForm(email);
        return new Email(email);
    }

    public static void validateForm(String requestEmail) {
        validateEmptyAndNull(requestEmail);
        validateEmailPattern(requestEmail);
    }

    private static void validateEmailPattern(String requestEmail) {
        if (!EMAIL_PATTERN.matcher(requestEmail).matches()) {
            throw new InvalidEmailException();
        }
    }

    private static void validateEmptyAndNull(String requestEmail) {
        if (Objects.isNull(requestEmail)) {
            throw new InvalidEmailException();
        }
    }
}
