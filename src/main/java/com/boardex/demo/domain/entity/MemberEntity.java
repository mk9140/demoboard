package com.boardex.demo.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "userId", length = 30, nullable = false)
	private String userId;

	@Column(name = "userPassword",length = 100, nullable = false)
	private String userPassword;

	@Column(name = "userName",length = 30, nullable = false)
	private String userName;

	@Column(name = "enabled",length = 1, nullable = false)
	private boolean enabled;

	@Column(name = "role", length = 30, nullable = false)
	private String role;

	@Builder

	public MemberEntity(Long id, String userId, String userPassword, String userName, boolean enabled, String role) {
		this.id = id;
		this.userId = userId;
		this.userPassword = userPassword;
		this.userName = userName;
		this.enabled = enabled;
		this.role = role;
	}
}
