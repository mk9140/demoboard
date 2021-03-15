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
import java.util.*;

@Service
@AllArgsConstructor
public class BoardService {

	private final BoardRepositoryInterface boardRepository;

	private static final int DISPLAY_PAGE_LENGTH = 5; // pagination
	private static final int DISPLAY_ENTITY_COUNT = 4; // 画面別表示するポストの数



	@Transactional //トランザクションを適用するアノテーション
	public Long savePost(BoardDto boardDto) {
		return boardRepository.save(boardDto.toEntity()).getArticleNumber();
		//save()　->DBのINSERT、UPDATE担当
	}



	/* 処理 - 掲示板ポストのリスト */
	@Transactional
	public List<BoardDto> getBoardList(Integer pageNum) {
		/* Sort.by(Sort.Direction.DESC, "[基準カラム名]" でポスト目録の並べ替え */
		Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(pageNum - 1, DISPLAY_ENTITY_COUNT, Sort.by(Sort.Direction.DESC, "articleNumber")));
		List<BoardEntity> boardEntities = page.getContent();
		List<BoardDto> boardDtoList = new ArrayList<>();

		for (BoardEntity boardEntity : boardEntities) {
			boardDtoList.add(this.convertEntityToDto(boardEntity)); //Controller <--> Service 間のデータやり取りはDTOでするため
		}
		return boardDtoList;
	}

	@Transactional
	public Long getBoardCount() {
		// エンティティの数（DB内のテーブルの行数）を返す
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

	/* ポスト確認 */
	public BoardDto getPost(Long articleNumber) {
		Optional<BoardEntity> boardEntityWrapper  = boardRepository.findById(articleNumber); //PKの値をwhere句に入れてデータを取る。
		BoardEntity boardEntity = boardEntityWrapper.get(); // get() :WrapperからEntityを取得
		return convertEntityToDto(boardEntity);
	}


	/* ポスト削除 */
	@Transactional
	public void deletePost(Long articleNumberBoardDto) {
		boardRepository.deleteById(articleNumberBoardDto);
	}

	/* ポスト検索*/
	@Transactional
	public List<BoardDto> searchPosts(int searchOption, String keyword) {
		List<BoardDto> boardDtoList = new ArrayList<>();
		List<BoardEntity> boardEntities = new ArrayList<>();
		switch (searchOption){
			case 1: // 検索基準：タイトル
				boardEntities  = boardRepository.findByTitleContaining(keyword , Sort.by(Sort.Direction.DESC, "articleNumber"));
				break;
			case 2:// 検索基準：作成者
				boardEntities  = boardRepository.findByWriterContaining(keyword , Sort.by(Sort.Direction.DESC, "articleNumber"));
				break;
			case 3:// 検索基準：内容
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

	public Map<String, Integer> getPrevEndPage(int curPageNum) {
		Map<String, Integer> otherBlock = new HashMap<>();


		Double totalEntityCount = Double.valueOf(this.getBoardCount());
		int totalPage = (int)(Math.ceil((totalEntityCount/ DISPLAY_ENTITY_COUNT)));

		if(curPageNum <= 0){
			curPageNum = 1;
		}

		int currentBlock = curPageNum % DISPLAY_PAGE_LENGTH == 0 ?
				curPageNum / DISPLAY_PAGE_LENGTH : (curPageNum / DISPLAY_PAGE_LENGTH) + 1;


		int tempBlock = currentBlock;
		if (currentBlock >= 2){
			currentBlock = currentBlock - 1;
		}

		int startPage = (currentBlock-1) * DISPLAY_PAGE_LENGTH + 1;
		int endPage = startPage+DISPLAY_PAGE_LENGTH -1;
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		if(startPage == 1 && tempBlock == 1){
			endPage = 1;
		}

		otherBlock.put("previous", endPage);

		return otherBlock;
	}


	public Map<String, Integer> getNextStartPage(int curPageNum) {
		Map<String, Integer> otherBlock = new HashMap<>();
		Double totalEntityCount = Double.valueOf(this.getBoardCount());
		int totalPage = (int)(Math.ceil((totalEntityCount/ DISPLAY_ENTITY_COUNT)));


		if(curPageNum <= 0){
			curPageNum = 1;
		}

		int currentBlock = curPageNum % DISPLAY_PAGE_LENGTH == 0 ?
				curPageNum / DISPLAY_PAGE_LENGTH : (curPageNum / DISPLAY_PAGE_LENGTH) + 1;

		int curStartPage = (currentBlock-1) * DISPLAY_PAGE_LENGTH + 1;

		currentBlock++;
		int nextStartPage = (currentBlock-1) * DISPLAY_PAGE_LENGTH + 1;
		otherBlock.put("next", nextStartPage);

		int endPage = curStartPage+DISPLAY_PAGE_LENGTH -1;
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		if (nextStartPage > endPage){
			nextStartPage = endPage;
		}

		otherBlock.put("total", totalPage);


		return otherBlock;
	}
}
