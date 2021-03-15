package com.boardex.demo.dto;

import com.boardex.demo.domain.entity.BoardEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class BoardDtoTest {

	@Test
	public void toEntity() {
		//given : DTOがhtmlから値を取る
		Long articleNumber = 99L;
		String writer = "test writer";
		String title = "test title";
		String content = "test content";
		BoardDto boardDto = new BoardDto();
		boardDto.setArticleNumber(articleNumber);
		boardDto.setWriter(writer);
		boardDto.setTitle(title);
		boardDto.setContent(content);

		//when : boardEntity化
		BoardEntity result = boardDto.toEntity();

		//then : memberEntityに格納されている値とテスト入力値が一致すべき
		Assertions.assertThat(result.getArticleNumber()).isEqualTo(boardDto.getArticleNumber());
		Assertions.assertThat(result.getWriter()).isEqualTo(boardDto.getWriter());
		Assertions.assertThat(result.getTitle()).isEqualTo(boardDto.getTitle());
		Assertions.assertThat(result.getContent()).isEqualTo(boardDto.getContent());
	}

}