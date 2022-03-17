package com.atdd.practice.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("해당 이메일에 맞는 유저를 가져온다.")
    @Test
    void test() {
        // given
        String userEmail = "xorals9448@gmail.com";

        // when
        Optional<Member> findMember = memberRepository.findMemberByEmail(userEmail);
        Member member = findMember.get();

        // then
        assertThat(member.getEmail()).isEqualTo(userEmail);
    }

}