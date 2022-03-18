package com.atdd.practice.member.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.atdd.practice.member.domain.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements CustomMemberRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existsByEmail(String email) {
        Integer integer = jpaQueryFactory.selectOne()
                .from(member)
                .where(member.email.eq(email))
                .fetchOne();

        return integer != null;
    }
}
