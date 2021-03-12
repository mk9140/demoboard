package com.boardex.demo.domain.repository;

import com.boardex.demo.domain.entity.BoardEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepositoryInterface extends JpaRepository<BoardEntity, Long> {
	List<BoardEntity> findByTitleContaining(String keyword, Sort articleNumber);

	List<BoardEntity> findByWriterContaining(String keyword, Sort articleNumber);

	List<BoardEntity> findByContentContaining(String keyword, Sort articleNumber);
}
