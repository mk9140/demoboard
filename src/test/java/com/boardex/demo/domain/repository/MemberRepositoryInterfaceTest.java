package com.boardex.demo.domain.repository;

import com.boardex.demo.domain.entity.MemberEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class MemberRepositoryInterfaceTest {
	@Autowired
	private MemberRepositoryInterface memberRepository;

	private MemberEntity memberEntity;

	@BeforeEach
	public void setUp(){
		//given すでにユーザーが登録されている
		memberEntity = MemberEntity.builder()
				.userId("testId")
				.userName("testUser")
				.userPassword("testPass")
				.enabled(true)
				.role("ROLE_MEMBER")
				.build();
		memberRepository.save(memberEntity);
	}

	@Test
	@DisplayName("会員ID重複確認")
	public void findByUserId(){
		//when 新しいユーザーが登録するこに、会員IDを入力
		MemberEntity result;
		String testId01 = "testId";
		String testId02 = "testid";
		String testId03 = "TESTID";

		//then すでに存在するIDの場合は登録不可
		result = memberRepository.findByUserId(testId01);
		Assertions.assertThat(result).isNotNull();

		result = memberRepository.findByUserId(testId02);
		Assertions.assertThat(result).isNull();

		result = memberRepository.findByUserId(testId03);
		Assertions.assertThat(result).isNull();
	}


}