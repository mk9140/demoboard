package com.boardex.demo.controller;

import com.boardex.demo.dto.BoardDto;
import com.boardex.demo.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@Controller
@AllArgsConstructor
public class BoardController {
	private BoardService boardService;


	/* 글 목록 페이지 */
	@GetMapping("/board/list")
	public String dispList(Model model) {
		List<BoardDto> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);
		return "board/list";
	}

	/* 글 작성 페이지 */
	@GetMapping("/board/post")
	public String dispWrite() {
		System.out.println("BoardController.dispWrite");
		return "board/write";
	}

	/* 글 작성 처리 */
	@PostMapping("/board/post")
	public String excWrite(BoardDto boardDto) {
		System.out.println("BoardController.excWrite");
		boardService.savePost(boardDto);
		return "redirect:/board/list";
	}

	/* 글 읽기 페이지 */
	@GetMapping("/board/list/{articleNumber}")
	public String detail(@PathVariable("articleNumber") Long articleNumber, Model model) {
		BoardDto boardDto = boardService.getPost(articleNumber);
		model.addAttribute("boardDto", boardDto);
		log.info("遷移/読み込み - " + articleNumber);
		return "board/read";
	}

	/* 글 수정 페이지 */
	@GetMapping("/board/edit/{articleNumber}")
	public String editPost(@PathVariable("articleNumber") Long articleNumber, Model model) {
		BoardDto boardDto = boardService.getPost(articleNumber);
		model.addAttribute("boardDto", boardDto);
		log.info("遷移/修正 - " + articleNumber);
		return "board/edit";
	}

	/* 글 수정 처리 */
	//	@PutMapping("/board/edit/{articleNumber}")
	// @PutMappingでは実行不可の為、明示的マッピング
	@RequestMapping(value = "/board/edit/{articleNumber}", produces = "application/json",  method=RequestMethod.PUT)
	public String excEdit(BoardDto boardDTO) {
		boardService.savePost(boardDTO);
		log.info("実行/修正 " + boardDTO.getArticleNumber());
		return "redirect:/board/list";
	}

	/* 글 삭제 처리 */
	@RequestMapping(value = "/board/{articleNumber}", produces = "application/json",  method=RequestMethod.DELETE)
	public String excDelete(@PathVariable("articleNumber") Long articleNumberBoardDto) {
		boardService.deletePost(articleNumberBoardDto);
		log.info("実行/削除 ");
		return "redirect:/board/list";
	}

}
