package com.boardex.demo.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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

	@Column(name = "userPassword",length = 30, nullable = false)
	private String userPassword;

	@Column(name = "userName",length = 30, nullable = false)
	private String userName;




}
