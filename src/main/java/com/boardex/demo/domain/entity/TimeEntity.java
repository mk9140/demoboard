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
 * データ操作時、自動的に年月日を修正するJPAのAuditingを使用
 * */
@Getter
@MappedSuperclass // (Entitiyだが)テーブルとマッピングせづに子クラスにマッピングの情報を承継するため
@EntityListeners(AuditingEntityListener.class) // JPA에게 Auditing기능을 사용한다는 것을 알리는 어노테이션
public class TimeEntity {

	@CreatedDate // Entityが生成される時の時間
	@Column(updatable = false) //生成時間は修正(update)不要
	private LocalDateTime createdDate; // 掲示板ポスト作成日

	@LastModifiedDate // Entity修正時間
	private LocalDateTime modifiedDate; // 掲示板ポスト修正日

}
