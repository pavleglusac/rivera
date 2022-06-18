package com.tim20.rivera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = "com.tim20.rivera")
public class RiveraApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiveraApplication.class, args);
	}

}
