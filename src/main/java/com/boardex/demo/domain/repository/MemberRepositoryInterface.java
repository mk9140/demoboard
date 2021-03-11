package com.boardex.demo.domain.repository;

import com.boardex.demo.domain.entity.MemberEntity;
import com.boardex.demo.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepositoryInterface extends JpaRepository<MemberEntity, Long> {
	MemberEntity findByUserId(String userId);
}
