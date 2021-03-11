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
	String userIdBlankMsg = "ID를 입력해 주세요";
	String userIdSpaceMsg ="ID는 공백없이 10자이내로 입력해 주세요";
	String userPasswordBlankMsg = "비밀번호를 입력해 주세요";
	String userPasswordSpaceMsg ="비밀번호는 공백없이 10자이내로 입력해 주세요";
	String userNameBlankMsg = "이름을 입력해 주세요";
	String userNameSpaceMsg = "이름은 공백없이 10자이내로 입력해 주세요";


	@Test
	@DisplayName("valid 테스트 : 입력값 공백포함")
	public void validationHandlingBlank() {
		MemberDto memberDto = new MemberDto();

		/* given 입력받은 값에 공백이 있는 경우 */
		String userId = "i d";
		String userPassword = "p w";
		String userName = "n e";
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
//			System.out.println("msg = " + next.getMessage());
		}

		Assertions.assertThat(messages).contains(userIdSpaceMsg, userPasswordSpaceMsg,userNameSpaceMsg);
	}

	@Test
	@DisplayName("valid 테스트 : 입력값 길이초과")
	public void validationHandlingOver() {
		MemberDto memberDto = new MemberDto();


		/* given 입력받은 값이 공백없는 10자리 이상 */
		String userId = "id1234567890";
		String userPassword = "pw1234567890";
		String userName = "ne1234567890";
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
//			System.out.println("msg = " + next.getMessage());
		}

		Assertions.assertThat(messages).contains(userIdSpaceMsg, userPasswordSpaceMsg,userNameSpaceMsg);

	}

	@Test
	@DisplayName("valid 테스트 : 입력값 없음")
	public void validationHandlingEmpty() {
		MemberDto memberDto = new MemberDto();


		/* given 입력받은 값이 없음 */
		String userId = "";
		String userPassword = "";
		String userName = "";
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
//			System.out.println("msg = " + next.getMessage());
		}

		Assertions.assertThat(messages).contains(userIdBlankMsg, userPasswordBlankMsg,userNameBlankMsg);

	}

	@Test
	@DisplayName("valid 테스트 : 정상")
	public void validationHandlingOK() {
		MemberDto memberDto = new MemberDto();


		/* given 입력받은 값이 없음 */
		String userId = "";
		String userPassword = "";
		String userName = "";
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
//			System.out.println("msg = " + next.getMessage());
		}

		Assertions.assertThat(messages).contains(userIdBlankMsg, userPasswordBlankMsg,userNameBlankMsg);
	}




}