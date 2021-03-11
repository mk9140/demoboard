package com.boardex.demo.dto;

import com.boardex.demo.domain.entity.MemberEntity;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberDto {
	private Long id; // for db

	@NotBlank(message = "ID를 입력해 주세요")
	@Pattern(regexp="(?=\\S+$).{1,10}", message = "ID는 공백없이 10자이내로 입력해 주세요")
	private String userId;

	@NotBlank(message = "비밀번호를 입력해 주세요")
	@Pattern(regexp="(?=\\S+$).{1,10}", message = "비밀번호는 공백없이 10자이내로 입력해 주세요")
	private String userPassword;

	@NotBlank(message = "이름을 입력해 주세요")
	@Pattern(regexp="(?=\\S+$).{1,10}", message = "이름은 공백없이 10자이내로 입력해 주세요")
	private String userName;

	private boolean enabled;

	private String role;

	public MemberEntity toEntity() {
		MemberEntity memberEntity = MemberEntity.builder()
				.id(id)
				.userId(userId)
				.userPassword(userPassword)
				.userName(userName)
				.enabled(enabled)
				.role(role)
				.build();
		return memberEntity;
	}

	@Builder
	public MemberDto(Long id, String userId, String userPassword,String userName, boolean enabled, String  role) {
		this.id = id;
		this.userId = userId;
		this.userPassword = userPassword;
		this.userName = userName;
		this.enabled = enabled;
		this.role = role;
	}
}