package com.boardex.demo.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
public class BoardEntity extends TimeEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // null 입력된 경우 DB가 알아서 auto_increment 해줌
	@Column(name = "articleNumber")
	private Long articleNumber; //글번호 (식별자)

	@Column(name = "writer", length = 100, nullable = false)
	private String writer; // 작성자(유저id)

	@Column(name = "title", length = 100, nullable = false)
	private String title; // 글제목

	@Column(name = "content",columnDefinition = "TEXT", nullable = false)
	private String content; // 글 내용

	@Builder // @Setter대신 빌더패턴을 사용함
	public BoardEntity(Long articleNumber, String writer, String title, String content) {
		this.articleNumber = articleNumber;
		this.writer = writer;
		this.title = title;
		this.content = content;
	}
}
