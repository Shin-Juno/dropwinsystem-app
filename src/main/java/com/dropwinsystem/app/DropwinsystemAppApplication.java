package com.dropwinsystem.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dropwinsystem.app.mapper")
public class DropwinsystemAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DropwinsystemAppApplication.class, args);
	}

}
