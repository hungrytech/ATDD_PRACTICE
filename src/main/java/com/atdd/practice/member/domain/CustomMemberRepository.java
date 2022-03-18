package com.atdd.practice.member.domain;

public interface CustomMemberRepository {

    boolean existsByEmail(String email);
}
