package com.boardex.demo.service;

import com.boardex.demo.domain.repository.MemberRepositoryInterface;
import com.boardex.demo.dto.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import java.util.*;


@Service
@AllArgsConstructor
public class MemberService {
	private MemberRepositoryInterface memberRepository;

	// 회원가입 시, 유효성 체크
	public Map<String, String> validationHandling(Errors errors) {
		Map<String, String> validatorResult = new HashMap<>();

		for (FieldError error : errors.getFieldErrors()) {
			String validKeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}

		return validatorResult;
	}

	// 회원가입
	public void memberInsert(MemberDto memberDto) {
		//TODO 구현
	}

}
