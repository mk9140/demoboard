package com.boardex.demo.dto;

import com.boardex.demo.domain.entity.BoardEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class BoardDtoTest {

	@Test
	public void toEntity() {
		//given : DTO가 html로부터 값을 받아오면
		Long articleNumber = 99L;
		String writer = "test writer";
		String title = "test title";
		String content = "test content";
		BoardDto boardDto = new BoardDto();
		boardDto.setArticleNumber(articleNumber);
		boardDto.setWriter(writer);
		boardDto.setTitle(title);
		boardDto.setContent(content);

		//when : boardEntity 객체에 값을 넣는다.
		BoardEntity result = boardDto.toEntity();

		//then : boardEntity 객체가 가지고 있는 값과 테스트로 입력한 DTO->엔티티 변환 값이 같아야 한다.
		Assertions.assertThat(result.getArticleNumber()).isEqualTo(boardDto.getArticleNumber());
		Assertions.assertThat(result.getWriter()).isEqualTo(boardDto.getWriter());
		Assertions.assertThat(result.getTitle()).isEqualTo(boardDto.getTitle());
		Assertions.assertThat(result.getContent()).isEqualTo(boardDto.getContent());
	}

}