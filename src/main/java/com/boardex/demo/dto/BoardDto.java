package com.boardex.demo.dto;

import com.boardex.demo.domain.entity.BoardEntity;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


/*
* 게시판 글 관련 데이터 전달 객체(DTO)
* */


@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {

	private Long articleNumber;
	private String writer;
	private String title;
	private String content;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;

	public BoardEntity toEntity() {
		BoardEntity boardEntity = BoardEntity.builder()
				.articleNumber(articleNumber)
				.writer(writer)
				.title(title)
				.content(content)
				.build();
		return boardEntity;
	}

	@Builder
 	public BoardDto(Long articleNumber, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate) {
     this.articleNumber = articleNumber;
     this.writer = writer;
     this.title = title;
     this.content = content;
     this.createdDate = createdDate;
     this.modifiedDate = modifiedDate;
 }

}
