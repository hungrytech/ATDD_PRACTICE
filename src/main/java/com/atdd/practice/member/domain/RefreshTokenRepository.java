package com.atdd.practice.member.domain;

import com.atdd.practice.common.security.infrastructure.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
