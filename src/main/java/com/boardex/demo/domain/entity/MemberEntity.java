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
	private String userId;

	@Column(length = 100, nullable = false)
	private String userPassword;

	@Column(length = 100, nullable = false)
	private String userName;




}
