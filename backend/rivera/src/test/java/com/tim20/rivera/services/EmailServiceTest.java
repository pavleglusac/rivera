package com.tim20.rivera.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Profile("test")
@Configuration
public class EmailServiceTest {
    @Bean
    @Primary
    public JavaMailSender javaMailSender() {
        JavaMailSender javaMailSender = mock(JavaMailSender.class);
        when(javaMailSender.createMimeMessage()).thenReturn(new MimeMessage((Session) null));
        return javaMailSender;
    }
}
