package com.boardex.demo.domain.repository;

import com.boardex.demo.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface MemberRepositoryInterface extends JpaRepository<MemberEntity, Long> {
	//会員IDを使ってDBからユーザー検索
	MemberEntity findByUserId(String userId);
}
