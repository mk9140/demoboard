package com.boardex.demo.service;

import com.boardex.demo.domain.entity.MemberEntity;
import com.boardex.demo.domain.repository.MemberRepositoryInterface;
import com.boardex.demo.dto.MemberDto;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import java.util.*;


@Service
@AllArgsConstructor
public class MemberService {
	private final MemberRepositoryInterface memberRepository;
	private final PasswordEncoder passwordEncoder; // パスワード暗号化

	// ユーザー登録時、入力値検証
	public Map<String, String> validationHandling(Errors errors) {
		Map<String, String> validatorResult = new HashMap<>();

		for (FieldError error : errors.getFieldErrors()) {
			String validKeyName = String.format("valid_%s", error.getField());
			validatorResult.put(validKeyName, error.getDefaultMessage());
		}

		return validatorResult;
	}

	// ユーザー登録時
	public String memberInsert(MemberDto memberDto) {

		//ユーザー登録時、入力したパスワード値を暗号化
		String encodedPassword = passwordEncoder.encode(memberDto.getUserPassword());
		//既存パスワード値を暗号化値に変更
		memberDto.setUserPassword(encodedPassword);
		//ユーザーテーブル"enable"カラム値"true"に変更
		memberDto.setEnabled(true);
		//ユーザー権限設定
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
