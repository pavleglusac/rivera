package com.tim20.rivera.services;

import com.tim20.rivera.dto.CottageDTO;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.repository.TagRepository;
import com.tim20.rivera.service.CottageService;
import com.tim20.rivera.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.io.IOException;
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
    TagRepository tagRepository;

    @Autowired
    TagService tagService;

    @Test
    @Transactional
    public void testUpdateFailed(){
        CottageDTO firstRead = cottageService.getById(4);
        CottageDTO secondRead = cottageService.getById(4);
        firstRead.setName("Prva");
        secondRead.setName("Druga");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Boolean> future1 = executor.submit(() -> {
            return cottageService.update(firstRead,null);
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Future<Boolean> future2 = executor.submit(() -> {
            return cottageService.update(secondRead,null);
        });
        try {
            future1.get();
        } catch (InterruptedException e) {
            assert false;
        } catch (ExecutionException e) {
            assert false;
        }
        try {
            future2.get();
        } catch (OptimisticLockException e){
            assert true;
        } catch (InterruptedException e) {
            assert false;
        } catch (ExecutionException e) {
            assert true
                    ;
        }
    }
}
