package com.tim20.rivera.service;


import com.tim20.rivera.dto.ClientRequestDTO;
import com.tim20.rivera.dto.OwnerRequestDTO;
import com.tim20.rivera.dto.ReservationReportDTO;
import com.tim20.rivera.model.*;
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
        mail.setFrom("mrsisatim20@outlook.com"/*ownerRequestDTO.getEmail()*/);
        mail.setSubject("Rivera - Registration for " + ownerRequestDTO.getType());
        String htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
        htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>Registration Request</h3>";
        htmlMsg += "<p style='text-align: left; font-size: 15px; font-family: sans-serif;'>" + ownerRequestDTO.mailPrint();
        htmlMsg += "<br><br><a style='font-size: 20px; text-decoration: none; font-family: sans-serif; padding: 10px; border: none; color: white; background-color:#16C79A;' href='http://localhost:3000/registration/" + ownerRequestDTO.getUsername() + "' style=''>VIEW REGISTRATION REQUEST</a></p></div>";
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
        mail.setFrom("mrsisatim20@outlook.com"/*reservationRepository.getById((reservationReportDTO.getId())).getRentable().getOwner().getEmail()*/);
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
    public void sendDiscountNotificaition(String username, String subject, String text, Rentable rentable) {
        try {
            System.out.println("Sending email...");
            System.out.println("http://localhost:8080" + rentable.getPictures().get(0));
            Person person = personService.findByUsername(username);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
            htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>" + subject + "</h3>";
            htmlMsg += "<img style='height: 200px; width: 400px; object-fit: cover; margin: auto' src='http://localhost:8080" + rentable.getPictures().get(0) + "' />";
            htmlMsg += "<p style='text-align: center; font-size: 15px; font-family: sans-serif;'>" + text + "</p>";
            htmlMsg += "<br><br><a style='font-size: 20px; margin: auto; text-align: center; text-decoration: none; font-family: sans-serif; padding: 25px; border: none; color: white; background-color:#16C79A;' href='http://localhost:3000/rentable/" + rentable.getId() + "'>RENTABLE DETAILS</a>";
            htmlMsg += "<hr style='width: 100%'/></div>";
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
    public void sendReservationNotificaition(Reservation reservation) throws MailException, MessagingException {
        System.out.println("Sending email to client...");
        MimeMessage mimeMessageClient = javaMailSender.createMimeMessage();
        MimeMessageHelper mailClient = new MimeMessageHelper(mimeMessageClient, "utf-8");
        String htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
        htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>Reservation details</h3>";
        htmlMsg += "<p style='font-size: 15px; font-family: sans-serif;'>" + getReservationTextClient(reservation) + "</p></div>";
        mailClient.setText(htmlMsg, true);
        mailClient.setTo(reservation.getClient().getEmail());
        mailClient.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mailClient.setSubject("Reservation Details");
        javaMailSender.send(mimeMessageClient);
        System.out.println("Email to client sent!");

        System.out.println("Sending email to owner...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
        htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
        htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>New Reservation</h3>";
        htmlMsg += "<p style='font-size: 15px; font-family: sans-serif;'>" + getReservationTextOwner(reservation) + "</p></div>";
        mail.setText(htmlMsg, true);
        mail.setTo(reservation.getRentable().getOwner().getEmail());
        mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        mail.setSubject("New Reservation");
        javaMailSender.send(mimeMessage);
        System.out.println("Email to owner sent!");
    }

    @Async
    public void assignPenaltyEmail(String responseText, Client client, Owner owner) {
        try {
            System.out.println("Sending email...");
            Person person = personService.findByUsername(client.getUsername());
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            String text = "Dear " + client.getUsername() + ",\n\nUnfortunately we must inform" +
                    " you that you have been assigned a penalty. Three of these penalties per month " +
                    "will stop you from using our services. Reason: " + responseText +
                    "\n\nSincerely,\n Rivera.";
            MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
            htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>" + "Penalty" + "</h3>";
            htmlMsg += "<p style='text-align: center; font-size: 15px; font-family: sans-serif;'>" + text + "</p></div>";
            mail.setText(htmlMsg, true);
            mail.setTo(person.getEmail());
            mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
            mail.setSubject("Penalty");
            javaMailSender.send(mimeMessage);
            System.out.println("Email sent!");
        } catch (Exception e) {
            System.out.println("email not sent");
        }

        try {
            System.out.println("Sending email...");
            Person person = personService.findByUsername(owner.getUsername());
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
            String text = "Dear " + owner.getUsername() + ",\n\nWe inform you that the user with username "
                    + client.getUsername() + " was assigned a penalty as per your request." +
                    "\n\nSincerely,\n Rivera.";
            String htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
            htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>" + "Penalty" + "</h3>";
            htmlMsg += "<p style='text-align: center; font-size: 15px; font-family: sans-serif;'>" + text + "</p></div>";
            mail.setText(htmlMsg, true);
            mail.setTo(person.getEmail());
            mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
            mail.setSubject("Penalty");
            javaMailSender.send(mimeMessage);
            System.out.println("Email sent!");
        } catch (Exception e) {
            System.out.println("email not sent");
        }
    }

    @Async
    public void complaintEmail(String responseText, Review review, Client client, Owner owner) {
        try {
            System.out.println("Sending email...");
            Person person = personService.findByUsername(client.getUsername());
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            String text = "Dear " + client.getUsername() + ",<br><br>We inform you that your complaint has just been reviewed by the admin team. " +
                    "The admin team has responded with: " + responseText +
                    "<br><br>Sincerely,<br>Rivera.";
            MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
            String htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
            htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>" + "Complaint" + "</h3>";
            htmlMsg += "<p style='text-align: center; font-size: 15px; font-family: sans-serif;'>" + text + "</p></div>";
            mail.setText(htmlMsg, true);
            mail.setTo(person.getEmail());
            mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
            mail.setSubject("Complaint");
            javaMailSender.send(mimeMessage);
            System.out.println("Email sent!");
        } catch (Exception e) {
            System.out.println("email not sent");
        }

        try {
            System.out.println("Sending email...");
            Person person = personService.findByUsername(owner.getUsername());
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, "utf-8");
            String text = "Dear " + owner.getUsername() + ",<br><br>We inform you that the user with username "
                    + client.getUsername() + " has just filed a complaint on you or your rentable with " +
                    " text \"" + review.getText() + "\". The admin team has responded with \"" + responseText + "\"" +
                    "<br><br>Sincerely,<br> Rivera.";
            String htmlMsg = "<div style='max-width: 600px; margin: auto; justify-content: center; align-items: center;'>";
            htmlMsg += "<h3 style='font-size: 30px; color:#16C79A; font-family: sans-serif; text-align: center'>" + "Complaint" + "</h3>";
            htmlMsg += "<p style='text-align: center; font-size: 15px; font-family: sans-serif;'>" + text + "</p></div>";
            mail.setText(htmlMsg, true);
            mail.setTo(person.getEmail());
            mail.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
            mail.setSubject("Complaint");
            javaMailSender.send(mimeMessage);
            System.out.println("Email sent!");
        } catch (Exception e) {
            System.out.println("email not sent");
        }
    }

    private String getReservationTextClient(Reservation reservation) {
        return "Dear " + reservation.getClient().getUsername() + ",<br><br>We are confirming " +
                "that you have successfully reserved " + reservation.getRentable().getName() + " for the period of "
                + reservation.getStartDateTime().toString().replace("T", " ") + " to " + reservation.getEndDateTime().toString().replace("T", " ") +
                ". Cost of your reservation was " + reservation.getPrice() +
                "$.<br><br>We are sure you will have good time!<br>Remember to leave a review after your reservation." +
                "If you can't make it, just cancel your reservation 3 days in advance in order to avoid getting a penalty." +
                "<br><br>Sincerely,<br> Rivera.";
    }

    private String getReservationTextOwner(Reservation reservation) {
        return "Dear " + reservation.getRentable().getOwner().getUsername() + ",<br><br>We are informing you that " +
                reservation.getClient().getName() + " has reserved " + reservation.getRentable().getName() + " for the period of "
                + reservation.getStartDateTime().toString().replace("T", " ") + " to " + reservation.getEndDateTime().toString().replace("T", " ") +
                ".<br>Cost of the reservation is " + reservation.getPrice() + "$.<br>Additional services client required are: " +
                String.join(", ", reservation.getAdditionalServices()) +
                ".<br><br>Sincerely,<br> Rivera.";
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
