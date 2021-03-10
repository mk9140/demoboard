package com.boardex.demo.domain.repository;

import com.boardex.demo.domain.entity.BoardEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // 메모리 db (h2)로 테스트를 함 testCompile('com.h2database:h2') 디펜던시 추가필요
class BoardRepositoryInterfaceTest {

	@Autowired
	private BoardRepositoryInterface boardRepository;
	private BoardEntity boardEntity;

	Long resultArticleNumber;
	@BeforeEach
	public void setUp() {
		//given : html로부터 입력받으면
		BoardEntity boardEntity = BoardEntity.builder()
				.content("test content")
				.title("test title")
				.writer("test writer")
				.build();

		//when : DB에 저장을 하고, 다시 받아온 articleNumber가
		resultArticleNumber = boardRepository.save(boardEntity).getArticleNumber();


	}

	@Test
	@DisplayName("글 저장 테스트")
	public void savePost() {
		//then 1이어야 한다
		assertThat(resultArticleNumber).isEqualTo(1L); //DB에서 자동으로 증가시주므로, 테스트케이스에서는 1
	}

	@Test
	@DisplayName("글 보기 테스트")
	void getPost() {
		//when : 저장된 정보를 글번호로 조회했을 때
		Optional<BoardEntity> boardEntityWrapper  = boardRepository.findById(resultArticleNumber); //PK값을 where조건으로 해서 데이터 가져오는 메서드
		BoardEntity result = boardEntityWrapper.get(); // get() : 레퍼로부터 엔티티를빼옴

		//입력값과 결과값이 같아야 한다.
		assertThat(resultArticleNumber).isEqualTo(result.getArticleNumber());
		assertThat("test title").isEqualTo(result.getTitle());
		assertThat("test content").isEqualTo(result.getContent());
		assertThat("test writer").isEqualTo(result.getWriter());



	}



}