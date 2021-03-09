package com.boardex.demo.domain.repository;

import com.boardex.demo.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface boardRepositoryInterface extends JpaRepository<BoardEntity, Long> {
}
