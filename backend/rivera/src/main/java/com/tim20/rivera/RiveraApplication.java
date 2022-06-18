package com.tim20.rivera;

import com.tim20.rivera.model.*;
import com.tim20.rivera.repository.*;
import com.tim20.rivera.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = "com.tim20.rivera")
public class RiveraApplication {

	public static void main(String[] args) {
		SpringApplication.run(RiveraApplication.class, args);
	}

}
