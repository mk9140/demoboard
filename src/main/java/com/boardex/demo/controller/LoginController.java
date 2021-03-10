package com.boardex.demo.controller;

import com.boardex.demo.dto.MemberDto;
import com.boardex.demo.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@AllArgsConstructor
public class LoginController {
	private MemberService memberService;

	// 로그인 페이지
	@GetMapping("/")
	public String toLogin() {
		return "/login/login";
//		return "redirect:/list";// 임시로

	}


	// 회원가입 페이지
	@GetMapping("/signup")
	public String toSingup(MemberDto memberDto) {
		return "/login/signup";
	}

	// 회원가입 처리
	@PostMapping("/signup")
	public String execSignup(@Valid MemberDto memberDto, Errors errors, Model model) {
		// @Vaild ? html입력값이 dto클래스로 캡슐화되어 넘어올 때, 유효성 체크 지시하는 어노테이션
		// Errors ? 유효성체크 결과 오류있으면 그에 대한 정보 저장
		System.out.println(memberDto.toString());

		if (errors.hasErrors()) { // hasErrors() 유효성체크에서 에러가 있는 경우
			// 회원가입 실패시, 입력 데이터는 유지하면서
			model.addAttribute("memberDto", memberDto);

			// 유효성 통과 못한 필드와 메시지를 핸들링하여 메세지로 표시해줌
			Map<String, String> validatorResult = memberService.validationHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}
			// 그대로 회원가입 페이지에 머무르기
			return "/login/signup";
		}
		//회원가입 로직 실행
		memberService.memberInsert(memberDto);

		//로그인화면으로 이동
		return "redirect:/login";
	}





	// 로그인 성공페이지
	@PostMapping("/login")
	public String excLogin() {

		return "/board";
	}


	// 로그인 성공페이지->게시글목록으로 이동
	@GetMapping("/user/login/success")
	public String toBoard() {
		return "redirect:/list";
	}

	// 로그인 실패 페이지
	@GetMapping("/user/login/result")
	public String toLoginFail() {
		return "/loginFail";
	}


	// 로그인 성공페이지
	@PostMapping("/test")
	public String toTest() {

		return "/test";
	}
}
