package com.tim20.rivera.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@EnableSwagger2
@Profile("dev")
public class SwaggerConfig {
//    @Bean
//    @Profile("dev")
//    public Docket swaggerApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.tim20.rivera.controller"))
//                .paths(PathSelectors.ant("/api/**"))
//                .build();
//    }
}
