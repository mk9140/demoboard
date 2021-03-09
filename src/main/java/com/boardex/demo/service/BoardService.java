package com.boardex.demo.service;


import com.boardex.demo.domain.entity.BoardEntity;
import com.boardex.demo.domain.repository.boardRepositoryInterface;
import com.boardex.demo.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardService {

	private boardRepositoryInterface boardRepository;

	@Transactional //선언적 트랜잭션. 트랜잭션 적용하는 어노테이션
	public Long savePost(BoardDto boardDto) {
		return boardRepository.save(boardDto.toEntity()).getArticleNumber();
		//save()함수->Jpa에서 정의. DB의 INSERT, UPDATE 담당
	}


	/* 컨트롤러와 서비스간 데이터 전달은 dto 객체로 하기 위해서
	*  레포지토리에서 가져온 엔티티를 반복문을 통해 dto로 변환
	* */
	@Transactional
	public List<BoardDto> getBoardList() {
		List<BoardEntity> boardEntities = boardRepository.findAll();
		List<BoardDto> boardDtoList = new ArrayList<>();

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
//				.createdDate(boardEntity.getCreatedDate())
				.build();
	}

}
