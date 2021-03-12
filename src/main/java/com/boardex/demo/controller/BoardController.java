package com.boardex.demo.controller;

import com.boardex.demo.dto.BoardDto;
import com.boardex.demo.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log
@Controller
@AllArgsConstructor
public class BoardController {
	private BoardService boardService;


//	/* 遷移 -  掲示板ポストのリスト画面 */
	@GetMapping("/board/list")
	public String dispList(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
		List<BoardDto> boardList = boardService.getBoardList(pageNum);
		Integer[] pageList = boardService.getPageList(pageNum);
		model.addAttribute("pageList", pageList);
		model.addAttribute("boardList", boardList);
		model.addAttribute("activePage", pageNum);

		model.addAttribute("prevPage", pageNum);
		model.addAttribute("nextPage", pageNum);


		return "board/list";
	}

	/* 遷移 - 書き込み画面 */
	@GetMapping("/board/post")
	public String dispWrite() {
		System.out.println("BoardController.dispWrite");
		return "board/write";
	}

	/* 実行 - 書き込み */
	@PostMapping("/board/post")
	public String excWrite(BoardDto boardDto) {
		System.out.println("BoardController.excWrite");
		boardService.savePost(boardDto);
		return "redirect:/board/list";
	}

	/* 遷移 - 掲示板ポストの確認 */
	@GetMapping("/board/list/{articleNumber}")
	public String detail(@PathVariable("articleNumber") Long articleNumber, Model model) {
		BoardDto boardDto = boardService.getPost(articleNumber);
		model.addAttribute("boardDto", boardDto);
		log.info("遷移/読み込み - " + articleNumber);
		return "board/read";
	}

	/* 遷移 - 掲示板ポストの修正画面 */
	@GetMapping("/board/edit/{articleNumber}")
	public String editPost(@PathVariable("articleNumber") Long articleNumber, Model model) {
		BoardDto boardDto = boardService.getPost(articleNumber);
		model.addAttribute("boardDto", boardDto);
		log.info("遷移/修正 - " + articleNumber);
		return "board/edit";
	}

	/* 実行 - 掲示板ポストの修正 */
	//	@PutMapping("/board/edit/{articleNumber}")
	// @PutMappingでは実行不可の為、明示的マッピング
	@RequestMapping(value = "/board/edit/{articleNumber}", produces = "application/json",  method=RequestMethod.PUT)
	public String excEdit(BoardDto boardDTO) {
		boardService.savePost(boardDTO);
		log.info("実行/修正 " + boardDTO.getArticleNumber());
		return "redirect:/board/list";
	}

	/*  実行 - 掲示板ポストの削除 */
	@RequestMapping(value = "/board/{articleNumber}", produces = "application/json",  method=RequestMethod.DELETE)
	public String excDelete(@PathVariable("articleNumber") Long articleNumberBoardDto) {
		boardService.deletePost(articleNumberBoardDto);
		log.info("実行/削除 ");
		return "redirect:/board/list";
	}

	/* 検索 */
	@GetMapping("/board/search")
	public String exeSearch(@RequestParam(value="searchOption") int searchOption, @RequestParam(value="keyword") String keyword, Model model) {
		List<BoardDto> boardDtoList = boardService.searchPosts(searchOption, keyword);
    	model.addAttribute("boardList", boardDtoList);
		return "board/list";

	}

	/* test */
	@PostMapping("/board/pageCheck")
	public void testfunc(@RequestBody String param ){

		System.out.println("dddzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzd = " + param);

	}



}
