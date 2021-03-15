package com.boardex.demo.dto;

import com.boardex.demo.domain.entity.MemberEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class MemberDtoTest {


	@Test
	public void toEntity() {
		//given : DTOがhtmlから値を取る
		Long id = 99L;
		String userId = "test userid";
		String userPassword = "test password";
		String userName = "test username";
		boolean enabled = true;
		String role = "ROLE_TEST";

		MemberDto memberDto = new MemberDto(id, userId, userPassword,userName, enabled, role);

		//when : memberEntityに変換
		MemberEntity result = memberDto.toEntity();


		//then : memberEntityに格納されている値とテスト入力値が一致すべき
		Assertions.assertThat(result.getId()).isEqualTo(memberDto.getId());
		Assertions.assertThat(result.getUserId()).isEqualTo(memberDto.getUserId());
		Assertions.assertThat(result.getUserPassword()).isEqualTo(memberDto.getUserPassword());
		Assertions.assertThat(result.getUserName()).isEqualTo(memberDto.getUserName());
		Assertions.assertThat(result.getRole()).isEqualTo(memberDto.getRole());
		Assertions.assertThat(result.isEnabled()).isEqualTo(memberDto.isEnabled());



	}


}