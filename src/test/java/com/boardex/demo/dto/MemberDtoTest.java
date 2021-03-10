package com.boardex.demo.dto;

import com.boardex.demo.domain.entity.BoardEntity;
import com.boardex.demo.domain.entity.MemberEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberDtoTest {
	BoardEntity boardEntity;


	@Test
	public void toEntity() {
		//given : DTO가 html로부터 값을 받아온다
		Long id = 99L;
		String userId = "test userid";
		String userPassword = "test password";
		String userName = "test username";
		boolean enabled = true;
		String role = "ROLE_TEST";

		MemberDto memberDto = new MemberDto(id, userId, userPassword,userName, enabled, role);

		//when : memberEntity객체형으로 리턴하면
		MemberEntity result = memberDto.toEntity();


		//then : memberEntity객체가 가지고 있는 값과 테스트로 입력한 값이 같아야 한다.
		Assertions.assertThat(result.getId()).isEqualTo(memberDto.getId());
		Assertions.assertThat(result.getUserId()).isEqualTo(memberDto.getUserId());
		Assertions.assertThat(result.getUserPassword()).isEqualTo(memberDto.getUserPassword());
		Assertions.assertThat(result.getUserName()).isEqualTo(memberDto.getUserName());
		Assertions.assertThat(result.getRole()).isEqualTo(memberDto.getRole());
		Assertions.assertThat(result.isEnabled()).isEqualTo(memberDto.isEnabled());



	}


}