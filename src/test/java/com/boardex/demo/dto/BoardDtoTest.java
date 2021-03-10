package com.boardex.demo.dto;

import com.boardex.demo.domain.entity.BoardEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BoardDtoTest {
	BoardEntity boardEntity;

	@Test
	public void toEntity() {
		//given : DTO가 html로부터 값을 받아오면
		Long articleNumber = 99L;
		String writer = "test writer";
		String title = "test title";
		String content = "test content";

		//when : boardEntity 객체에 값을 넣는다.
		BoardEntity boardEntity = BoardEntity.builder()
				.articleNumber(articleNumber)
				.writer(writer)
				.title(title)
				.content(content)
				.build();

		//then : boardEntity 객체가 가지고 있는 값과 테스트로 입력한 값이 같아야 한다.
		Assertions.assertThat(boardEntity.getArticleNumber()).isEqualTo(articleNumber);
		Assertions.assertThat(boardEntity.getWriter()).isEqualTo(writer);
		Assertions.assertThat(boardEntity.getTitle()).isEqualTo(title);
		Assertions.assertThat(boardEntity.getContent()).isEqualTo(content);
	}

}