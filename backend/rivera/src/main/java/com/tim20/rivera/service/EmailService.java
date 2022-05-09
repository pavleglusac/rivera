package com.tim20.rivera.service;


import com.tim20.rivera.dto.OwnerRequestDTO;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;


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

    /*
     * Anotacija za oznacavanje asinhronog zadatka
     * Vise informacija na: https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#scheduling
     */
    @Async
    public void sendNotificaitionAsync(OwnerRequestDTO ownerRequestDTO) throws MailException, InterruptedException {
        System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        System.out.println(ownerRequestDTO.getEmail()+"-------------------");
        mail.setTo(env.getProperty("spring.mail.username"));
        mail.setFrom(ownerRequestDTO.getEmail());
        mail.setSubject("Application");
        String text = "";
        text = "Application for " + ownerRequestDTO.getType() + ":";
        text += ownerRequestDTO.mailPrint()+"\n";
        text += "http://localhost:3000/registration/"+ownerRequestDTO.getUsername();
        mail.setText(text);
        javaMailSender.send(mail);

        System.out.println("Email poslat!");
    }

    public void sendNotificaitionSync(Person user) throws MailException, InterruptedException, MessagingException {
        System.out.println("Sync metoda se izvrsava u istom Threadu koji je i prihvatio zahtev. Thread id: " + Thread.currentThread().getId());
        //Simulacija duze aktivnosti da bi se uocila razlika
        Thread.sleep(10000);
        System.out.println("Slanje emaila...");
        MimeMessageHelper message = new MimeMessageHelper(javaMailSender.createMimeMessage(), true, "UTF-8");
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Primer slanja emaila pomocu asinhronog Spring taska");
        mail.setText("Pozdrav " + user.getName() + ",\n\nhvala što pratiš ISA.");
        javaMailSender.send(mail);

        System.out.println("Email poslat!");
    }

    @Async
    public void sendNotificaitionToUsername(String username, String subject, String text) throws MailException, InterruptedException {
        System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();

        Person person = personService.findByUsername(username);
        mail.setTo(person.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject(subject);
        mail.setText(text);
        javaMailSender.send(mail);
        System.out.println("Email poslat!");
    }

    @Async
    public void sendNotificaitionToOwnerUsername(String username, String subject, String text) throws MailException, InterruptedException {
        System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();

        Owner owner = ownerService.findByUsername(username);
        mail.setTo(owner.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject(subject);
        mail.setText(text);
        javaMailSender.send(mail);
        System.out.println("Email poslat!");
    }

}
