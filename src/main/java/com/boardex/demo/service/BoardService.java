package com.boardex.demo.service;


import com.boardex.demo.domain.entity.BoardEntity;
import com.boardex.demo.domain.repository.BoardRepositoryInterface;
import com.boardex.demo.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BoardService {

	private final BoardRepositoryInterface boardRepository;

	private static final int DISPLAY_PAGE_LENGTH = 300; // pagination 블럭 표시갯수
	private static final int DISPLAY_ENTITY_COUNT = 4; //페이지당 게시글 수



	@Transactional //선언적 트랜잭션. 트랜잭션 적용하는 어노테이션
	public Long savePost(BoardDto boardDto) {
		return boardRepository.save(boardDto.toEntity()).getArticleNumber();
		//save()함수->Jpa에서 정의. DB의 INSERT, UPDATE 담당
	}


	/* 컨트롤러와 서비스간 데이터 전달은 dto 객체로 하기 위해서
	 *  레포지토리에서 가져온 엔티티를 반복문을 통해 dto로 변환
	 * */
	/* 게시글 목록 가져오기 */
	@Transactional
	public List<BoardDto> getBoardList(Integer pageNum) {
		/* Sort.by(Sort.Direction.DESC, "[基準カラム名]" でポスト目録の並べ替え */
//		List<BoardEntity> boardEntities = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "articleNumber"));
		Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(pageNum - 1, DISPLAY_ENTITY_COUNT, Sort.by(Sort.Direction.DESC, "articleNumber")));
		List<BoardEntity> boardEntities = page.getContent();
		List<BoardDto> boardDtoList = new ArrayList<>();

//		if (boardEntities.isEmpty()) return boardDtoList;

		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}
		return boardDtoList;
	}

	@Transactional
	public Long getBoardCount() {
		// 엔티티 갯수(DB의 테이블의 행 수) 반환
		return boardRepository.count();
	}

	public Integer[] getPageList(Integer curPageNum) {
		Integer[] pageList = new Integer[DISPLAY_PAGE_LENGTH];

		Double totalEntityCount = Double.valueOf(this.getBoardCount());

		int totalPage = (int)(Math.ceil((totalEntityCount/ DISPLAY_ENTITY_COUNT)));

		if(curPageNum <= 0){
			curPageNum = 1;
		}

		int currentBlock = curPageNum % DISPLAY_PAGE_LENGTH == 0 ?
				curPageNum / DISPLAY_PAGE_LENGTH : (curPageNum / DISPLAY_PAGE_LENGTH) + 1;

		int startPage = (currentBlock-1) * DISPLAY_PAGE_LENGTH + 1;
		int endPage = startPage+DISPLAY_PAGE_LENGTH -1;
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		for (int val = startPage, idx = 0; val <= endPage; val++, idx++) {
			pageList[idx] = val;
		}
		return pageList;
	}

	/* 게시글 내용보기 */
	public BoardDto getPost(Long articleNumber) {
		Optional<BoardEntity> boardEntityWrapper  = boardRepository.findById(articleNumber); //PK값을 where조건으로 해서 데이터 가져오는 메서드
		BoardEntity boardEntity = boardEntityWrapper.get(); // get() : 레퍼로부터 엔티티를빼옴
		return convertEntityToDto(boardEntity);
	}


	/* 게시글 삭제 */
	@Transactional
	public void deletePost(Long articleNumberBoardDto) {
		boardRepository.deleteById(articleNumberBoardDto);
	}

	@Transactional
	public List<BoardDto> searchPosts(int searchOption, String keyword) {
		List<BoardDto> boardDtoList = new ArrayList<>();
		List<BoardEntity> boardEntities = new ArrayList<>();
		switch (searchOption){
			case 1: // title search
				boardEntities  = boardRepository.findByTitleContaining(keyword , Sort.by(Sort.Direction.DESC, "articleNumber"));
				break;
			case 2:
				boardEntities  = boardRepository.findByWriterContaining(keyword , Sort.by(Sort.Direction.DESC, "articleNumber"));
				break;
			case 3:
				boardEntities  = boardRepository.findByContentContaining(keyword , Sort.by(Sort.Direction.DESC, "articleNumber"));
				break;
			default:
				break;

		}
		if (boardEntities.isEmpty()) return boardDtoList;
		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity));
		}
		return boardDtoList;

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
