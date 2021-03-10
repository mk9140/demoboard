package com.boardex.demo.config;

import com.boardex.demo.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/*
 * 로그인시 회원 인증 처리
 * */

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final DataSource dataSource; // 어플리케이션 설정파일의 datasource부의 정보를 사용할 수 있게 주입

	@Bean
	public PasswordEncoder passwordEncoder() { // 비밀번호 암호화
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				//GET 메소드는 문제가 없는데 POST 메소드만 동작을 안한다,
				//CSRF (Cross Site Request Forgery) 설정이 디폴트 (Default)로 활성화 (Enable)되기 때문이다.
				//연습프로젝트에선 이를 비활성화 시킨다
				//보안에 대한 공부도 필요
				.csrf().disable()

				.authorizeRequests()
				// 누구나 접근가능한 페이지 설정
				// 기본화면 (/), 회원가입화면 (/signup)은 권한없이 접근가능(permitAll())
				.antMatchers("/board/*").hasRole("MEMBER")
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated()
				.and()
				//권한 없으면 볼 url (기본화면 /)
				.formLogin()
//				.loginPage("/member/login")
				.usernameParameter("userName").passwordParameter("userPassword")
				.defaultSuccessUrl("/board/list")
//				.defaultSuccessUrl("/user/login/result")
				.and()
				.logout()
				.logoutSuccessUrl("/")
				.permitAll();
	}

	/* 인증처리 쿼리 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				// 설정파일에 등록된 DB사용
				.dataSource(dataSource)
				// 스프링이 인증처리 할 때 비밀번호 암호화를 알아서 해주도록
				.passwordEncoder(passwordEncoder())
				//인증 처리
				//출력결과는 사용자이름/비번/enabled로 순서를 지켜야 한다
				.usersByUsernameQuery("select userName as username,userPassword as password,enabled "
						+ "from member "
						+ "where userId = ?")
				//권한 처리
				//출력결과는 사용자이름/권한명
/*				.authoritiesByUsernameQuery("select member.userName "
						+ "from userrole "
						+ "inner join member "
						+ "on userrole.userId = member.id "
						+ "inner join role "
						+ "on userrole.roleId = role.id "
						+ "where member.userId = ?");*/
				.authoritiesByUsernameQuery(
						"select userName as username, role "
								+"from member "
								+"where userId = ? ");

	}
}