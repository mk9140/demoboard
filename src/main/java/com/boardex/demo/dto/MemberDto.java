package com.boardex.demo.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
	private Long id; // for db

	@NotBlank(message = "ID를 입력해 주세요")
	@Pattern(regexp="(?=\\S+$).{1,10}", message = "공백없이 10자이내로 입력해 주세요")
	private String userId;

	@NotBlank(message = "비밀번호를 입력해 주세요")
	@Pattern(regexp="(?=\\S+$).{1,10}", message = "공백없이 10자이내로 입력해 주세요")
	private String userPassword;

	@NotBlank(message = "이름을 입력해 주세요")
	@Pattern(regexp="(?=\\S+$).{1,10}", message = "공백없이 10자이내로 입력해 주세요")
	private String userName;

	@Builder
	public MemberDto(Long id, String userId, String userPassword,String userName) {
		this.id = id;
		this.userId = userId;
		this.userPassword = userPassword;
		this.userName = userName;
	}
}