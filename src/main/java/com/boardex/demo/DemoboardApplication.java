package com.boardex.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@EnableJpaAuditing // JPA의 Auditing활성화 위한 어노테이션
@SpringBootApplication
public class DemoboardApplication {


	public static void main(String[] args) {
		SpringApplication.run(DemoboardApplication.class, args);
	}

	@Bean
	/* RESTFUL方式のため。(PUT、DELETE、PATCH) */
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}

}
