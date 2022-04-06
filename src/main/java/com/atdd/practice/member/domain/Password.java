package com.atdd.practice.member.domain;

import com.atdd.practice.member.application.exception.InvalidPasswordException;
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
public class Password {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!~@#$%^*+=-]).{8,}$");

    @Column(name = "password", nullable = false)
    private String value;

    private Password(String encodedPassword) {
        this.value = encodedPassword;
    }

    public static Password of(String encodedPassword) {
        return new Password(encodedPassword);
    }

    public static void validatePassword(String password) {
        validateEmptyAndNull(password);
        validatePasswordPattern(password);
    }

    private static void validateEmptyAndNull(String password) {
        if ("".equals(password) || Objects.isNull(password)) {
            throw new InvalidPasswordException();
        }
    }

    private static void validatePasswordPattern(String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new InvalidPasswordException();
        }
    }

}
