/*
package com.boardex.demo.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Setter
@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "role")
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name",length = 30, nullable = false)
	private String name;


	*/
/* member 테이블과 다대다 매칭(many to many) - 양방향 *//*

	@ManyToMany(mappedBy = "roles")
	private List<MemberEntity> members;


}
*/
