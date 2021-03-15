package com.boardex.demo.domain.repository;

import com.boardex.demo.domain.entity.BoardEntity;
import com.boardex.demo.dto.BoardDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // メモリDB使用(H2) testCompile('com.h2database:h2')
class BoardRepositoryInterfaceTest {

	@Autowired
	private BoardRepositoryInterface boardRepository;
	private BoardEntity boardEntity;

	Long resultArticleNumber;
	@BeforeEach
	public void setUp() {
		boardRepository.deleteAll();

		//given : htmlから値を取ると
		BoardEntity boardEntity = BoardEntity.builder()
				.content("test content")
				.title("test title")
				.writer("test writer")
				.build();

		//when : DBに格納したポストのarticleNumberは
		resultArticleNumber = boardRepository.save(boardEntity).getArticleNumber();
	}
	@AfterEach
	public void resetRepo (){
		boardRepository.deleteAll();

	}

	@Test
	@DisplayName("ポスト格納")
	public void savePost() {
		// then 1である
		assertThat(resultArticleNumber).isEqualTo(1L);
	}

	@Test
	@DisplayName("ポスト確認")
	void getPost() {
		//when : ポストをarticleNumberで検索した時
		Optional<BoardEntity> boardEntityWrapper  = boardRepository.findById(resultArticleNumber);
		BoardEntity result = boardEntityWrapper.get();

		//then 入力値と結果は一致すべき
		assertThat(resultArticleNumber).isEqualTo(result.getArticleNumber());
		assertThat("test title").isEqualTo(result.getTitle());
		assertThat("test content").isEqualTo(result.getContent());
		assertThat("test writer").isEqualTo(result.getWriter());
	}

	@Test
	@DisplayName("ポスト修正")
	void editPost(){
		// ポスト作成と同じ仕組み
		// 既存存在するポストにPKも一緒に入力するとUPDATE
		boardEntity = BoardEntity.builder()
				.content("edit content")
				.title("edit title")
				.writer("edit writer")
				.articleNumber(resultArticleNumber)
				.build();
		boardRepository.save(boardEntity);


		Optional<BoardEntity> boardEntityWrapper  = boardRepository.findById(resultArticleNumber);
		BoardEntity result = boardEntityWrapper.get();
		assertThat(resultArticleNumber).isEqualTo(result.getArticleNumber());
		assertThat("test title").isNotEqualTo(result.getTitle());
		assertThat("test content").isNotEqualTo(result.getContent());
		assertThat("test writer").isNotEqualTo(result.getWriter());
		assertThat("edit title").isEqualTo(result.getTitle());
		assertThat("edit content").isEqualTo(result.getContent());
		assertThat("edit writer").isEqualTo(result.getWriter());
	}


	@Test
	@DisplayName("ポスト削除")
	public void postDelete(){
		// ポスト削除
		boardRepository.deleteById(resultArticleNumber);

		// 削除されたポストのPKで検査してみる
		Optional<BoardEntity> result  = boardRepository.findById(resultArticleNumber);

		// 検査結果無し
		assertThat(result).isEqualTo(Optional.empty());

	}



	@Test
	@DisplayName("ポスト検索 - タイトル")
	public void searchTitle(){
		List<BoardDto> boardDtoList = new ArrayList<>();
		List<BoardEntity> boardEntities;
		//ポスト入力
		boardEntity = BoardEntity.builder()
				.content("content1")
				.title("abcdefg")
				.writer("taro")
				.build();
		boardRepository.save(boardEntity);

		boardEntity = BoardEntity.builder()
				.content("content2")
				.title("abcdQWER")
				.writer("tanaka")
				.build();
		boardRepository.save(boardEntity);

		boardEntity = BoardEntity.builder()
				.content("content3")
				.title("zxcvdQWER")
				.writer("takahashi")
				.build();
		boardRepository.save(boardEntity);


		//タイトルに"abcdefg"が含まれているポスト
		boardEntities  = boardRepository.findByTitleContaining("abcdefg" , Sort.by(Sort.Direction.DESC, "articleNumber"));
		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}
		assertThat(boardDtoList.size()).isEqualTo(1);
		boardDtoList.clear();


		//タイトルに"abcd"が含まれているポスト
		boardEntities  = boardRepository.findByTitleContaining("abcd" , Sort.by(Sort.Direction.DESC, "articleNumber"));

		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}
		assertThat(boardDtoList.size()).isEqualTo(2);
		boardDtoList.clear();

		//タイトルに"QWER"が含まれているポスト
		boardEntities  = boardRepository.findByTitleContaining("QWER" , Sort.by(Sort.Direction.DESC, "articleNumber"));

		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}
		assertThat(boardDtoList.size()).isEqualTo(2);
		boardDtoList.clear();

		//タイトルに"d"が含まれているポスト
		boardEntities  = boardRepository.findByTitleContaining("d" , Sort.by(Sort.Direction.DESC, "articleNumber"));

		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}
		assertThat(boardDtoList.size()).isEqualTo(3);
		boardDtoList.clear();
	}


	@Test
	@DisplayName("ポスト検索 - 作成者")
	public void searchWriter(){
		List<BoardDto> boardDtoList = new ArrayList<>();
		List<BoardEntity> boardEntities;
		//ポスト入力
		boardEntity = BoardEntity.builder()
				.content("content1")
				.title("abcdefg")
				.writer("nishida")
				.build();
		boardRepository.save(boardEntity);

		boardEntity = BoardEntity.builder()
				.content("content2")
				.title("abcdQWER")
				.writer("tanaka")
				.build();
		boardRepository.save(boardEntity);

		boardEntity = BoardEntity.builder()
				.content("content3")
				.title("zxcvdQWER")
				.writer("takahashi")
				.build();
		boardRepository.save(boardEntity);


		//作成者名に"nishida"が含まれているポスト
		boardEntities  = boardRepository.findByWriterContaining("nishida" , Sort.by(Sort.Direction.DESC, "articleNumber"));
		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}
		assertThat(boardDtoList.size()).isEqualTo(1);
		boardDtoList.clear();


		//タイトルに"shi"が含まれているポスト
		boardEntities  = boardRepository.findByWriterContaining("shi" , Sort.by(Sort.Direction.DESC, "articleNumber"));

		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}
		assertThat(boardDtoList.size()).isEqualTo(2);
		boardDtoList.clear();


		//タイトルに"ta"が含まれているポスト
		boardEntities  = boardRepository.findByWriterContaining("ta" , Sort.by(Sort.Direction.DESC, "articleNumber"));

		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}
		assertThat(boardDtoList.size()).isEqualTo(2);
		boardDtoList.clear();

	}

	@Test
	@DisplayName("ポスト検索 - 内容")
	public void searchContent(){
		List<BoardDto> boardDtoList = new ArrayList<>();
		List<BoardEntity> boardEntities;
		//ポスト入力
		boardEntity = BoardEntity.builder()
				.content("これはテストです")
				.title("abcdefg")
				.writer("nishida")
				.build();
		boardRepository.save(boardEntity);

		boardEntity = BoardEntity.builder()
				.content("テストする方が良い")
				.title("abcdQWER")
				.writer("tanaka")
				.build();
		boardRepository.save(boardEntity);

		boardEntity = BoardEntity.builder()
				.content("これが良さそう")
				.title("zxcvdQWER")
				.writer("takahashi")
				.build();
		boardRepository.save(boardEntity);


		//作成者名に"テスト"が含まれているポスト
		boardEntities  = boardRepository.findByContentContaining("テスト" , Sort.by(Sort.Direction.DESC, "articleNumber"));
		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}
		assertThat(boardDtoList.size()).isEqualTo(2);
		boardDtoList.clear();


		//作成者名に"良"が含まれているポスト
		boardEntities  = boardRepository.findByContentContaining("良" , Sort.by(Sort.Direction.DESC, "articleNumber"));
		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}
		assertThat(boardDtoList.size()).isEqualTo(2);
		boardDtoList.clear();

		//作成者名に"良"が含まれているポスト
		boardEntities  = boardRepository.findByContentContaining("良さ" , Sort.by(Sort.Direction.DESC, "articleNumber"));
		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}
		assertThat(boardDtoList.size()).isEqualTo(1);
		boardDtoList.clear();
	}



	private BoardDto convertEntityToDto(BoardEntity boardEntity) {
		return BoardDto.builder()
				.articleNumber(boardEntity.getArticleNumber())
				.title(boardEntity.getTitle())
				.content(boardEntity.getContent())
				.writer(boardEntity.getWriter())
				.createdDate(boardEntity.getCreatedDate())
				.build();
	}
}