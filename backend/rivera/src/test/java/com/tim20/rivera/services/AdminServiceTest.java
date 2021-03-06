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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.tim20.rivera.constants.PersonConstants.*;

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
            ret = adminService.resolveTerminationRequest(person.getUsername(), true, request.getId());
            return ret;
        });
        Future<Boolean> future2 = executor.submit(() -> {
            boolean ret = false;
            ret = adminService.resolveTerminationRequest(person.getUsername(), true, request.getId());
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
    public void testReviewLockingSuccess() throws InterruptedException {
        Review review = reviewRepository.findById(1).get();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Boolean> future1 = executor.submit(() -> {
            boolean ret;
            ret = adminService.resolveReview(review.getId(), false);
            return ret;
        });
        Thread.sleep(5000);
        Future<Boolean> future2 = executor.submit(() -> {
            boolean ret;
            ret = adminService.resolveReview(review.getId(), false);
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
            assert !r2;
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
