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
	@GeneratedValue(strategy = GenerationType.IDENTITY) //
	@Column(name = "articleNumber")
	private Long articleNumber;

	@Column(name = "writer", length = 100, nullable = false)
	private String writer;

	@Column(name = "title", length = 100, nullable = false)
	private String title;

	@Column(name = "content",columnDefinition = "TEXT", nullable = false)
	private String content;

	@Builder
	public BoardEntity(Long articleNumber, String writer, String title, String content) {
		this.articleNumber = articleNumber;
		this.writer = writer;
		this.title = title;
		this.content = content;
	}
}
