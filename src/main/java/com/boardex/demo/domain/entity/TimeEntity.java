package com.boardex.demo.domain.entity;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
 * 데이터 조작시 자동으로 날짜 수정해주는 JPA의 Auditing을 사용
 * */
@Getter
@MappedSuperclass // (엔티티 이지만)테이블로 매핑하지 않고, 자식 클래스(엔티티)에게 매핑 정보를 상속하기 위한 어노테이션
@EntityListeners(AuditingEntityListener.class) // JPA에게 Auditing기능을 사용한다는 것을 알리는 어노테이션
public class TimeEntity {

	@CreatedDate // 엔티티가 처음 저장될 때 생성일 주입하는 어노테이션
	@Column(updatable = false) // 생성일은 수정(update)필요 없으므로 false
	private LocalDateTime createdDate; // 글 최초 작성일

	@LastModifiedDate // 엔티티가 수정될 때, 수정일자를 주입하는 어노테이션
	private LocalDateTime modifiedDate; // 글 수정일

}
