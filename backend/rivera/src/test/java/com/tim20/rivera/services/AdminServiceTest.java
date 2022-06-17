package com.tim20.rivera.services;

import com.tim20.rivera.model.Person;
import com.tim20.rivera.model.TerminationRequest;
import com.tim20.rivera.model.TerminationStatus;
import com.tim20.rivera.repository.PersonRepository;
import com.tim20.rivera.repository.TerminationRepository;
import com.tim20.rivera.service.AdminService;
import com.tim20.rivera.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static com.tim20.rivera.constants.PersonConstants.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AdminServiceTest {

    @Mock
    TerminationRepository terminationRepository;

    @Mock
    PersonRepository personRepository;

    @Mock
    EmailService emailService;

    @InjectMocks
    AdminService adminService;

    @Test
    public void testTerminationLocking() {
        Person person = createPerson1();
        TerminationRequest request = createTerminationRequest1(person);
        when(personRepository.findByUsername("pera")).thenReturn(person);
        when(personRepository.save(person)).thenReturn(person);


        System.out.println("radi li ovo");

        AtomicInteger counter = new AtomicInteger();
        when(terminationRepository.findById(1)).thenReturn(request);
        when(terminationRepository.save(request)).then(x -> {
            System.out.println(counter.get());
            if(counter.get() == 0)  {
                System.out.println("SLEEEP");
                counter.getAndIncrement();
                Thread.sleep(1000);
            }
            return request;
        });

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(() -> {
            boolean ret = adminService.resolveTerminationRequest(person.getUsername(), true, request.getId(), "blabla");
            System.out.println(ret);
        });

        Future<?> future2 = executor.submit(() -> {
            boolean ret = adminService.resolveTerminationRequest(person.getUsername(), true, request.getId(), "stastablabla");
            System.out.println(ret);
        });

        try {
            future1.get();
            future2.get();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private Person createPerson1() {
        Person person = new Person();
        person.setUsername(p1Username);
        person.setEmail(p1Email);
        person.setName(p1Name);
        person.setSurname(p1Surname);
        person.setPassword(p1Password);
        return person;
    }

    private TerminationRequest createTerminationRequest1(Person person) {
        TerminationRequest request = new TerminationRequest();
        request.setId(1);
        request.setPerson(person);
        request.setStatus(TerminationStatus.PENDING);
        request.setVersion(0);
        request.setDescription("bla bla");
        return request;
    }

}
