package com.boardex.demo.controller;

import com.boardex.demo.dto.BoardDto;
import com.boardex.demo.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

	@GetMapping("/board/list/{articleNumber}")
 	public String detail(@PathVariable("articleNumber") Long articleNumber, Model model) {
     BoardDto boardDto = boardService.getPost(articleNumber);

     model.addAttribute("boardDto", boardDto);
     return "board/read";
 }

}
