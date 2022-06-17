package com.tim20.rivera.services;

import com.tim20.rivera.model.Person;
import com.tim20.rivera.model.Review;
import com.tim20.rivera.model.TerminationRequest;
import com.tim20.rivera.model.TerminationStatus;
import com.tim20.rivera.repository.PersonRepository;
import com.tim20.rivera.repository.ReviewRepository;
import com.tim20.rivera.repository.TerminationRepository;
import com.tim20.rivera.service.AdminService;
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
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static com.tim20.rivera.constants.PersonConstants.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AdminServiceTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    TerminationRepository terminationRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    AdminService adminService;

   @Test
   @Transactional
    public void testTerminationLockingFail() throws InterruptedException {
        Person person = personRepository.findByUsername("pera");
        TerminationRequest request = terminationRepository.findById(1);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Boolean> future1 = executor.submit(() -> {
            boolean ret = false;
            ret = adminService.resolveTerminationRequest(person.getUsername(), true, request.getId(), "blabla");
            return ret;
        });
        Future<Boolean> future2 = executor.submit(() -> {
            boolean ret = false;
            ret = adminService.resolveTerminationRequest(person.getUsername(), true, request.getId(), "statsatat");
            return ret;
        });

        try {
            boolean r1 = future1.get();
            assert r1;
            try {
                boolean r2 = future2.get();
                assert false;
            }  catch (Exception ex) {
                assert true;
            }
        } catch (Exception e) {
            try {
                boolean r2 = future2.get();
                assert r2;
            } catch (Exception e2) {
                assert false;
            }
        }
    }

    @Test
    @Transactional
    public void testTerminationLockingSuccess() throws InterruptedException {
        Person person = personRepository.findByUsername("pera");
        TerminationRequest request = terminationRepository.findById(1);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Boolean> future1 = executor.submit(() -> {
            boolean ret = false;
            ret = adminService.resolveTerminationRequest(person.getUsername(), true, request.getId(), "blabla");
            System.out.println(ret);
            return ret;
        });
        Thread.sleep(500);
        Future<Boolean> future2 = executor.submit(() -> {
            boolean ret = false;
            ret = adminService.resolveTerminationRequest(person.getUsername(), true, request.getId(), "statsatat");
            System.out.println(ret);
            return ret;
        });

        try {
            boolean r1 = future1.get();
            assert r1;
        } catch (Exception e) {
            assert false;
        }
        try{
            boolean r2 = future2.get();
            assert r2;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    @Transactional
    public void testReviewLockingFail() throws InterruptedException {
        Review review = reviewRepository.findById(1).get();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Boolean> future1 = executor.submit(() -> {
            boolean ret = false;
            ret = adminService.resolveReview(review.getId(), "response", false);
            return ret;
        });
        Future<Boolean> future2 = executor.submit(() -> {
            boolean ret = false;
            ret = adminService.resolveReview(review.getId(), "response dva", false);
            return ret;
        });

        try {
            boolean r1 = future1.get();
            System.out.println(r1);
            System.out.println(review.getVersion());
            assert r1;
            try {
                boolean r2 = future2.get();
                System.out.println(r2);
                assert r2;
            }  catch (Exception ex) {
                assert true;
            }
        } catch (Exception e) {
            try {
                boolean r2 = future2.get();
                System.out.println(review.getVersion());
                System.out.println(r2);
                assert r2;
            } catch (Exception e2) {
                assert true;
            }
        }
    }

    @Test
    @Transactional
    public void testReviewLockingSuccess() throws InterruptedException {
        Review review = reviewRepository.findById(1).get();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Boolean> future1 = executor.submit(() -> {
            boolean ret = false;
            ret = adminService.resolveReview(review.getId(), "response", false);
            return ret;
        });
        Thread.sleep(1500);
        Future<Boolean> future2 = executor.submit(() -> {
            boolean ret = false;
            ret = adminService.resolveReview(review.getId(), "response dva", false);
            return ret;
        });

        try {
            boolean r1 = future1.get();
            assert r1;
        } catch (Exception e) {
            assert false;
        }
        try{
            boolean r2 = future2.get();
            assert r2;
        } catch (Exception e) {
            assert false;
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
