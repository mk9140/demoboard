package com.boardex.demo.domain.repository;

import com.boardex.demo.domain.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepositoryInterface {

	MemberEntity signUp(MemberEntity member);
}
