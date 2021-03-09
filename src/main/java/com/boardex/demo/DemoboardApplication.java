package com.boardex.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA의 Auditing활성화 위한 어노테이션
@SpringBootApplication
public class DemoboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoboardApplication.class, args);
	}

}
