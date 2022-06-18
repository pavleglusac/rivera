package com.tim20.rivera.services;

import com.tim20.rivera.dto.PersonDTO;
import com.tim20.rivera.model.Client;
import com.tim20.rivera.service.ClientService;
import com.tim20.rivera.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private PersonService personService;
    @Autowired
    private ClientService clientService;

    @Test
    @Transactional
    public void testClientUpdateFailed() {
        Client client1 = clientService.findByUsername("pera");
        Client client2 = clientService.findByUsername("pera");

        client1.setPhoneNumber("065 123123");
        PersonDTO dto1 = personService.personToDTO(client1);
        client2.setPhoneNumber("065 456456");
        PersonDTO dto2 = personService.personToDTO(client2);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<Boolean> future1 = executor.submit(() -> personService.updatePerson(dto1));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Future<Boolean> future2 = executor.submit(() -> personService.updatePerson(dto2));
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
