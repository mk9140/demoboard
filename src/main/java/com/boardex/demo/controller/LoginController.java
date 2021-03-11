package com.boardex.demo.controller;

import com.boardex.demo.dto.MemberDto;
import com.boardex.demo.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Map;

@Controller
@AllArgsConstructor
public class LoginController {
	private final MemberService memberService;

	// 遷移 - 初期画面
	@GetMapping("/")
	public String toLogin() {
		return "index";

	}

	// 遷移 - ログイン画面
	@GetMapping("/member/login")
	public String dispLogin() {
		return "login/login";

	}


	// 遷移 - 会員登録画面
	@GetMapping("/member/signup")
	public String toSingup(MemberDto memberDto) {
		return "login/signup";
	}


	// 実行 - 会員ID重複確認
	@ResponseBody
	@PostMapping(value="/member/checkId")
	public boolean execIdCheck( MemberDto memberDto) {
		boolean isExist=true;
		isExist = memberService.checkId(memberDto.getUserId());
		return isExist;
	}



	// 実行 - 会員登録
	@PostMapping("/member/signup")
	public String execSignup(@Valid MemberDto memberDto, Errors errors, Model model) {
		// @Vaild ? 有効性チェックをおこなうことを指示するアノテーション
		// Errors ? 有効性チェックの結果、エラーを格納

		//有効性エラーある場合
		if (errors.hasErrors()) { //
			// 記入データは保存
			model.addAttribute("memberDto", memberDto);

			// エラーハンドリング
			Map<String, String> validatorResult = memberService.validationHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}
			// 会員登録画面へ遷移
			return "login/signup";
		}

		//会員登録を行う
		String rst = memberService.memberInsert(memberDto);
		System.out.println("rst = " + rst);

		//ログイン画面へ遷移
		return "redirect:/member/login";
	}





}
