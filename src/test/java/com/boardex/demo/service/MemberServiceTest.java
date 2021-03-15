package com.boardex.demo.service;

import com.boardex.demo.dto.MemberDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberServiceTest {
	@Autowired
	private Validator validatorInjected;

	/* 참조 given DTO 클래스에서 설정한 메세지들 */
	String userIdBlankMsg = "IDを入力してください。";
	String userIdSpaceMsg ="IDはスペース無で10文字以内にしてください。";
	String userPasswordBlankMsg = "パスワード入力してください。";
	String userPasswordSpaceMsg ="パスワードはスペース無で10文字以内にしてください。";
	String userNameBlankMsg = "名前を入力してください";
	String userNameSpaceMsg = "名前はスペース無で半角10文字以内にしてください。";


	@Test
	@DisplayName("valid テスト : 入力値にスペースあり")
	public void validationHandlingBlank() {
		MemberDto memberDto = new MemberDto();

		/* given 入力値にスペースあり */
		String userId = "i d";
		String userPassword = "p w";
		String userName = "n e";
		memberDto.setUserId(userId);
		memberDto.setUserPassword(userPassword);
		memberDto.setUserName(userName);

		//when : memberDtoを検証
		Set<ConstraintViolation<MemberDto>> validate = validatorInjected.validate(memberDto);


		//then エラー表示
		Iterator<ConstraintViolation<MemberDto>> iterator = validate.iterator();
		List<String> messages = new ArrayList<>();

		while (iterator.hasNext()) {
			ConstraintViolation<MemberDto> next = iterator.next();
			messages.add(next.getMessage());
		}

		Assertions.assertThat(messages).contains(userIdSpaceMsg, userPasswordSpaceMsg,userNameSpaceMsg);
	}

	@Test
	@DisplayName("valid テスト : 入力文字数制限")
	public void validationHandlingOver() {
		MemberDto memberDto = new MemberDto();


		/* given 入力値はが10文字以上 */
		String userId = "id1234567890";
		String userPassword = "pw1234567890";
		String userName = "ne1234567890";
		memberDto.setUserId(userId);
		memberDto.setUserPassword(userPassword);
		memberDto.setUserName(userName);

		//when : memberDtoを検証
		Set<ConstraintViolation<MemberDto>> validate = validatorInjected.validate(memberDto);


		//then エラー表示
		Iterator<ConstraintViolation<MemberDto>> iterator = validate.iterator();
		List<String> messages = new ArrayList<>();

		while (iterator.hasNext()) {
			ConstraintViolation<MemberDto> next = iterator.next();
			messages.add(next.getMessage());
		}

		Assertions.assertThat(messages).contains(userIdSpaceMsg, userPasswordSpaceMsg,userNameSpaceMsg);

	}

	@Test
	@DisplayName("valid テスト : 入力値無し")
	public void validationHandlingEmpty() {
		MemberDto memberDto = new MemberDto();


		/* given 入力値無し */
		String userId = "";
		String userPassword = "";
		String userName = "";
		memberDto.setUserId(userId);
		memberDto.setUserPassword(userPassword);
		memberDto.setUserName(userName);

		//when : memberDtoを検証
		Set<ConstraintViolation<MemberDto>> validate = validatorInjected.validate(memberDto);


		//then エラー表示
		Iterator<ConstraintViolation<MemberDto>> iterator = validate.iterator();
		List<String> messages = new ArrayList<>();

		while (iterator.hasNext()) {
			ConstraintViolation<MemberDto> next = iterator.next();
			messages.add(next.getMessage());
//			System.out.println("msg = " + next.getMessage());
		}

		Assertions.assertThat(messages).contains(userIdBlankMsg, userPasswordBlankMsg,userNameBlankMsg);

	}

	@Test
	@DisplayName("valid テスト : 異常なし")
	public void validationHandlingOK() {
		MemberDto memberDto = new MemberDto();


		/* given 入力値異常なし */
		String userId = "abcd1234";
		String userPassword = "qwer1234";
		String userName = "minki";
		memberDto.setUserId(userId);
		memberDto.setUserPassword(userPassword);
		memberDto.setUserName(userName);

		//when : 값을 입력받은 memberDto의 유효성을 검사한다
		Set<ConstraintViolation<MemberDto>> validate = validatorInjected.validate(memberDto);

		//then 입력값은 값에 따라 에러 메시지를 출력한다
		Iterator<ConstraintViolation<MemberDto>> iterator = validate.iterator();
		List<String> messages = new ArrayList<>();

		while (iterator.hasNext()) {
			ConstraintViolation<MemberDto> next = iterator.next();
			messages.add(next.getMessage());
		}

		Assertions.assertThat(messages).isEmpty();
	}




}