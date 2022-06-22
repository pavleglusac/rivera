package com.tim20.rivera.services;

import com.tim20.rivera.dto.ClientReservationDTO;
import com.tim20.rivera.dto.ReservationSearch;
import com.tim20.rivera.model.Client;
import com.tim20.rivera.service.ClientService;
import com.tim20.rivera.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReservationServiceTest {
    @Autowired
    ReservationService reservationService;
    @Autowired
    ClientService clientService;

    @Test
    @Transactional
    public void testClientReservations() {
        List<ClientReservationDTO> reservations = reservationService.getClientReservations("pera");
        int numberOfAdventures = 0;
        for(ClientReservationDTO dto : reservations)
            if(dto.getEntity().name.equals("Mountain Fishing"))
                numberOfAdventures++;
        assert numberOfAdventures == 6;
        assert reservations.size() == 9;
    }

    @Test
    @Transactional
    public void testCalculatePrice() {
        LocalDateTime start = LocalDateTime.parse("20/06/2022 18:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        LocalDateTime end = LocalDateTime.parse("20/06/2022 20:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        Client client = clientService.findByUsername("pera");
        double price = reservationService.calculatePriceForReservation(client, 1, start, end);
        assert price == 19.0;
    }

    @Test
    @Transactional
    public void testReservationFilter() {
        ReservationSearch search = new ReservationSearch();
        search.setSearch("");
        search.setDate("2022-05-21");
        search.setUpcoming("date");
        assert reservationService.getReservations("pera", search).size() == 4;
    }
}
