package com.tim20.rivera.services;

import com.tim20.rivera.dto.CottageDTO;
import com.tim20.rivera.model.Client;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.repository.ClientRepository;
import com.tim20.rivera.repository.ReservationRepository;
import com.tim20.rivera.repository.TagRepository;
import com.tim20.rivera.service.CottageService;
import com.tim20.rivera.service.ReservationService;
import com.tim20.rivera.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CottageServiceTest {

    @Autowired
    CottageService cottageService;
    @Autowired
    ReservationService reservationService;
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    TagRepository tagRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    TagService tagService;

    @Test
    @Transactional
    public void testReserveFailed() {
        CottageDTO firstRead = cottageService.getById(4);
        CottageDTO secondRead = cottageService.getById(4);
        firstRead.setName("Prva");
        secondRead.setName("Druga");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try{
        Future<Boolean> future1 = executor.submit(() -> reservationService.addReservation(clientRepository.findByUsername("pera"),
                4, LocalDateTime.now(),
                LocalDateTime.now().plusDays(2), 5d,new ArrayList<String >(),null) == null);

        Future<Boolean> future2 = executor.submit(() -> reservationService.addReservation(clientRepository.findByUsername("pera"),
                4, LocalDateTime.now(),
                LocalDateTime.now().plusDays(2), 5d,new ArrayList<String >(),null) == null);
        try {
            future1.get();
        } catch (InterruptedException | ExecutionException e) {
            assert false;
        }
        try {
            future2.get();
        } catch (InterruptedException e) {
            assert false;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(reservationRepository.findAll().size()+"*******************");
            if(reservationRepository.findAll().size() != 13){
                assert false;
            }
            assert true;
        }catch (Exception e){
            assert true;
        }

    }



    @Test
    @Transactional
    public void testUpdateFailed2() {
        CottageDTO firstRead = cottageService.getById(4);
        CottageDTO secondRead = cottageService.getById(4);
        firstRead.setName("Prva");
        secondRead.setName("Druga");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Boolean> future1 = executor.submit(() -> cottageService.update(firstRead, null));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Future<Boolean> future2 = executor.submit(() -> cottageService.update(secondRead, null));
        try {
            future1.get();
        } catch (InterruptedException | ExecutionException e) {
            assert false;
        }
        try {
            future2.get();
        } catch (OptimisticLockException | ExecutionException e) {
            assert true;
        } catch (InterruptedException e) {
            assert false;
        }
    }
}
