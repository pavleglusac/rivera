package com.tim20.rivera.service;


import com.tim20.rivera.dto.ClientRequestDTO;
import com.tim20.rivera.dto.OwnerRequestDTO;
import com.tim20.rivera.dto.ReservationReportDTO;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.model.Person;
import com.tim20.rivera.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Objects;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    /*
     * Koriscenje klase za ocitavanje vrednosti iz application.properties fajla
     */
    @Autowired
    private Environment env;

    @Autowired
    private PersonService personService;

    @Autowired
    private OwnerService ownerService;
    @Autowired
    private ReservationRepository reservationRepository;

    /*
     * Anotacija za oznacavanje asinhronog zadatka
     * Vise informacija na: https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
     */
    @Async
    public void sendNotificaitionAsync(OwnerRequestDTO ownerRequestDTO) throws MailException, MessagingException {
        System.out.println("Sending email...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
        mail.setTo(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setFrom(ownerRequestDTO.getEmail());
        mail.setSubject("Rivera - Registration for " + ownerRequestDTO.getType());
        String htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
        htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>Registration Request</h3>";
        htmlMsg += "<p style='text-align: left; font-size: 15px; font-family: sans-serif;'>" + ownerRequestDTO.mailPrint();
        htmlMsg += "<br><br><a style='font-size: 20px; text-decoration: none; font-family: sans-serif; padding: 10px; border: none; color: white; background-color:#16C79A;' href='http://localhost:3000/registration/" + ownerRequestDTO.getUsername() + "' style=''>VIEW REPORT</a></p></div>";
        mail.setText(htmlMsg, true);
        javaMailSender.send(mimeMessage);
        System.out.println("Email sent!");
    }

    @Async
    public void sendReportAsync(ReservationReportDTO reservationReportDTO) throws MailException, MessagingException {
        System.out.println("Sending email...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
        mail.setTo(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setFrom(reservationRepository.getById((reservationReportDTO.getId())).getRentable().getOwner().getEmail());
        mail.setSubject("Rivera - Sanction");
        String htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
        htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>Reservation Report</h3>";
        htmlMsg += "<p style='text-align: center; font-size: 15px; font-family: sans-serif;'>Report type: " + reservationReportDTO.getReservationReportType() + "<br>Client showed up: " + reservationReportDTO.getShowedUp() + "<br>Sanction: " + reservationReportDTO.getSanction() + "<br>" + reservationReportDTO.getText();
        htmlMsg += "<br><br><a style='font-size: 20px; text-decoration: none; font-family: sans-serif; padding: 10px; border: none; color: white; background-color:#16C79A;' href='http://localhost:3000/viewReport/" + reservationReportDTO.getId() + "' style=''>VIEW REPORT</a></p></div>";
        mail.setText(htmlMsg, true);
        javaMailSender.send(mimeMessage);
        System.out.println("Email sent!");
    }

    @Async
    public void sendNotificaitionClientAsync(ClientRequestDTO clientRequestDTO) throws MailException, MessagingException {
        System.out.println("Sending email...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
        htmlMsg += "<img style='height: 300px; width: 600px; object-fit: cover;' src='https://images.unsplash.com/photo-1493787039806-2edcbe808750?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80' />";
        htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>Welcome!</h3>";
        htmlMsg += "<p style='text-align: center; font-size: 15px; font-family: sans-serif;'>Your registration was a success. All you need to do now is to click on the activation link in order to join our community. You will be able to search cottages, boats and fishing classes from various sources, manage your calendar and reserve entities in just a few seconds! So, what are you waiting for?";
        htmlMsg += "<br><br><a style='font-size: 20px; text-decoration: none; font-family: sans-serif; padding: 10px; border: none; color: white; background-color:#16C79A;' href='http://localhost:3000/clientValidation/" + clientRequestDTO.getUsername() + "' style=''>ACTIVATION LINK</a></p></div>";
        mail.setText(htmlMsg, true);
        System.out.println(clientRequestDTO.getEmail() + "-------------------");
        mail.setTo(clientRequestDTO.getEmail());
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject("Rivera - Account Activation link");
        javaMailSender.send(mimeMessage);
        System.out.println("Email sent!");
    }

    @Async
    public void sendNotificaitionToUsername(String username, String subject, String text) {
        try {
            System.out.println("Sending email...");
            Person person = personService.findByUsername(username);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
            htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>" + subject + "</h3>";
            htmlMsg += "<p style='text-align: center; font-size: 15px; font-family: sans-serif;'>" + text + "</p></div>";
            mail.setText(htmlMsg, true);
            mail.setTo(person.getEmail());
            mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
            mail.setSubject(subject);
            javaMailSender.send(mimeMessage);
            System.out.println("Email sent!");
        } catch (Exception e) {
            System.out.println("email not sent");
        }
    }

    @Async
    public void sendNotificaitionToOwnerUsername(String username, String subject, String text) throws MailException, MessagingException {
        System.out.println("Sending email...");
        Owner owner = ownerService.findByUsername(username);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
        String htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
        htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>" + subject + "</h3>";
        htmlMsg += "<p style='text-align: center; font-size: 15px; font-family: sans-serif;'>" + text + "</p></div>";
        mail.setText(htmlMsg, true);
        mail.setTo(owner.getEmail());
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject(subject);
        javaMailSender.send(mimeMessage);
        System.out.println("Email sent!");
    }

}
