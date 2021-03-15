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
 * ログイン認証処理
 * */

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// アプリ設定ファイル(application.properties)のdatasource部の情報を使用できるように依存性収入
	private final DataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() { // 비밀번호 암호화
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				//GETは問題ないがPOST方式では動作しない時
				//CSRF(Cross Site Request Forgery)設定がデフォルトとして活性化(Enable)されている為。
				//これを無効かにする。
				.csrf().disable()

				.authorizeRequests()
				// 掲示板関連画面は"MEMBER"権限必要→DBには"ROLE_MEMBER"形式で格納
				.antMatchers("/board/*").hasRole("MEMBER")
				// 誰でも確認できる画面（権限無し）設定
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated()
				.and()
				//権限が無い時、ログイン画面へ遷移→ログイン処理を行うURL
				.formLogin()
				.loginPage("/member/login")
				//ログイン時、ユーザーを特定できるようにしてする必要がある
				.usernameParameter("userId").passwordParameter("userPassword")
				//ログイン成功時、遷移する画面
				.defaultSuccessUrl("/board/list")
				.and()
				//ログアウト
				.logout()
				.logoutSuccessUrl("/")
				.permitAll();
	}

	/* 認証処理 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				// 設定ファイルのDB情報使用
				.dataSource(dataSource)
				// パスワード暗号化
				.passwordEncoder(passwordEncoder())
				//ログインクエリー
				//注意：出力結果は「ユーザーを特定できるカラム」/パスワード/enabled順
				.usersByUsernameQuery("select userId,userPassword as password,enabled "
						+ "from member "
						+ "where userId = ?")
				//権限クエリー
				//出力結果は 「ユーザーを特定できるカラム」/権限名("ROLE_XXX")
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