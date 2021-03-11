package com.boardex.demo.service;

import com.boardex.demo.domain.entity.MemberEntity;
import com.boardex.demo.domain.repository.MemberRepositoryInterface;
import com.boardex.demo.dto.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import java.util.*;


@Service
@AllArgsConstructor
public class MemberService {
	private final MemberRepositoryInterface memberRepository;
	private final PasswordEncoder passwordEncoder; // 비밀번호암호화

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
	public String memberInsert(MemberDto memberDto) {

		//회원등록 페이지에서 입력한 비밀번호를 암호화
		String encodedPassword = passwordEncoder.encode(memberDto.getUserPassword());
		//암호화된 비밀번호로 갱신
		memberDto.setUserPassword(encodedPassword);
		//유저의 계정 활성화 상태를 1로 갱신
		memberDto.setEnabled(true);
		//유저 role 설정
		memberDto.setRole("ROLE_MEMBER");

		return memberRepository.save(memberDto.toEntity()).getUserId();
	}


	public boolean checkId(String userId) {
		boolean isExist = true;
		MemberEntity memberEntity = memberRepository.findByUserId(userId);
		if (memberEntity == null) {
			isExist = false;
		}
		return isExist;
	}
}
