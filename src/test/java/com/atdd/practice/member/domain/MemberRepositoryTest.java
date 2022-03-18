package com.atdd.practice.member.domain;

import com.atdd.practice.common.config.QueryDslConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static com.atdd.practice.member.fixture.MemberFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@Import(QueryDslConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("해당 이메일에 맞는 유저를 가져온다.")
    @Test
    void find_by_email() {
        // when
        Optional<Member> findMember = memberRepository.findMemberByEmail(ADMIN_MEMBER_EMAIL);
        Member member = findMember.get();

        // then
        assertThat(member.getEmail()).isEqualTo(ADMIN_MEMBER_EMAIL);
    }

    @DisplayName("회원을 저장한다.")
    @Test
    void save_member() {
        // when
        Member savedMember = memberRepository.save(MEMBER_CUSTOMER);

        // then
        assertThat(savedMember.getEmail()).isEqualTo(MEMBER_CUSTOMER.getEmail());
    }

    @DisplayName("해당 이메일이 증복되었는지 여부를 확인한다.")
    @CsvSource(value = {
            ADMIN_MEMBER_EMAIL + " | true",
            CUSTOMER_MEMBER_EMAIL + " | false"},
            delimiter = '|')
    @ParameterizedTest
    void exists_email(String email, boolean expected) {
        // when
        boolean result = memberRepository.existsByEmail(email);

        // then
        assertThat(result).isEqualTo(expected);
    }


}