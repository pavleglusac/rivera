package com.tim20.rivera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@ComponentScan(basePackages = "com.tim20.rivera")
public class RiveraApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiveraApplication.class, args);
	}

}
