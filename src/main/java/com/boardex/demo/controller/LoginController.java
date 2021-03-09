package com.boardex.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	// 회원가입 페이지
	@GetMapping("/user/signup")
	public String toSingup() {
		return "/signup";
	}

	// 회원가입 처리
	@PostMapping("/user/signup")
	public String excSignup() {
		return "redirect:/";
	}

	// 로그인 페이지
	@GetMapping("/")
	public String toLogin() {
//		return "/login/login";
		return "redirect:/list";// 임시로

	}


	// 로그인 성공페이지
	@PostMapping("/login")
	public String excLogin() {

		return "/board";
	}


	// 로그인 성공페이지
	@GetMapping("/user/login/success")
	public String toBoard() {
		return "/board";
	}

	// 로그인 실패 페이지
	@GetMapping("/user/login/result")
	public String toLoginFail() {
		return "/loginFail";
	}

}
