package com.tim20.rivera.controller;

import com.tim20.rivera.dto.*;
import com.tim20.rivera.model.Client;
import com.tim20.rivera.model.Reservation;
import com.tim20.rivera.model.ReviewType;
import com.tim20.rivera.service.ClientService;
import com.tim20.rivera.service.DiscountService;
import com.tim20.rivera.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping(path = "/get-clients")
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @PostMapping(path = "add-client")
    public ResponseEntity<String> addClient(ClientDTO clientDTO) {
        clientService.addClient(clientDTO);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping(path = "get-client-by-username")
    public ClientRequestDTO getClientByUsername(@RequestParam("username") String username) {
        return clientService.getClientByUsername(username);
    }

    @GetMapping(path = "get-client")
    public ClientDTO getClient(@RequestParam("id") String id) {
        return clientService.getClient(id);
    }

    @GetMapping(path = "check-if-client-exists")
    public boolean checkIfClientExists(@RequestParam("username") String username) {
        return clientService.clientExists(username);
    }

    @PostMapping(path = "update-client")
    public ResponseEntity<String> updateClient(ClientDTO clientDTO) {
        clientService.updateClient(clientDTO);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "update-client-photo")
    public ResponseEntity<String> updateClientPhoto(@RequestParam("username") String username, @RequestPart(value = "image") MultipartFile image) throws IOException {
        clientService.updateClientPhoto(username, image);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "activate-client")
    public Client activateClient(@RequestParam("username") String username) {
        System.out.println("activate");
        return clientService.activateClient(username);
    }

    @GetMapping(path = "get-subscribed-entities")
    public List<EntityDTO> getSubscribedEntities(@RequestParam("username") String username, String search) {
        return clientService.getSubscribedEntities(username, search);
    }

    @PostMapping(path = "is-subscribed")
    public Boolean isSubscribed(@RequestParam("username") String username, Integer id) {
        return clientService.isSubscribed(username, id);
    }

    @PostMapping(path = "subscribe")
    public ResponseEntity<String> subscribe(@RequestParam("username") String username, Integer id) {
        clientService.subscribe(username, id);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "unsubscribe")
    public ResponseEntity<String> unsubscribe(@RequestParam("username") String username, Integer id) {
        clientService.unsubscribe(username, id);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping(path = "get-reservations")
    public List<ClientReservationDTO> getReservations(@RequestParam("username") String username, ReservationSearch search) {
        return reservationService.getReservations(username, search);
    }

    @GetMapping(path = "get-client-loyalty")
    public ClientLoyaltyDTO getClientLoyalty(@RequestParam("username") String username) {
        return clientService.getClientLoyalty(username);
    }

    @GetMapping(path = "get-reservation-price")
    public Double getReservationPrice(@RequestParam("username") String username, Integer rentableId, String start, String end) {
        Client client = clientService.findByUsername(username);
        LocalDateTime startDateTime = LocalDateTime.parse(start.split("\\.")[0].replace("T", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDateTime = LocalDateTime.parse(end.split("\\.")[0].replace("T", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return reservationService.calculatePriceForReservation(client, rentableId, startDateTime, endDateTime);
    }

    @GetMapping(path = "didClientReserveEarlier")
    public boolean didClientReserveEarlier(@RequestParam("username") String username, Integer rentableId,
                                           String start, String end) {
        Client client = clientService.findByUsername(username);
        LocalDateTime startDateTime = clientService.parseDate(start);
        LocalDateTime endDateTime = clientService.parseDate(end);
        return clientService.didClientReserveEarlier(client, rentableId, startDateTime, endDateTime);
    }

    @PostMapping(path = "reserve")
    public ResponseEntity<String> reserve(@RequestParam("username") String username, Integer rentableId,
                                          String start, String end, Double price, String additionalServices, String discountId) {
        Client client = clientService.findByUsername(username);
        LocalDateTime startDateTime = clientService.parseDate(start);
        LocalDateTime endDateTime = clientService.parseDate(end);
        if(reservationService.isPeriodReserved(rentableId.toString(),startDateTime,endDateTime)){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("greska");
        }
        Reservation reservation = reservationService.addReservation(client, rentableId,
                startDateTime, endDateTime, price, List.of(additionalServices.split("\\|")), discountId);
        clientService.addReservation(username, reservation);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "cancelReservation")
    public ResponseEntity<String> cancelReservation(@RequestParam("username") String username, Integer reservationId) {
        Client client = clientService.findByUsername(username);
        clientService.cancelReservation(client, reservationId);
        reservationService.cancelReservation(reservationId);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "reviewReservation")
    public ResponseEntity<String> reviewReservation(@RequestParam("username") String username, Integer reservationId, String reviewFor, String rating, String reviewText) {
        Client client = clientService.findByUsername(username);
        reservationService.addReview(reservationId, client, reviewFor, reviewText, Double.parseDouble(rating), ReviewType.REVIEW_WITH_RATING);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PostMapping(path = "complainOnReservation")
    public ResponseEntity<String> complainOnReservation(@RequestParam("username") String username, Integer reservationId, String reviewFor, String reviewText) {
        Client client = clientService.findByUsername(username);
        reservationService.addReview(reservationId, client, reviewFor, reviewText, 0, ReviewType.COMPLAINT);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping(path = "getClientReviews")
    public List<ClientReviewDTO> getReviews(@RequestParam("username") String username) {
        return clientService.getReviews(username);
    }
}
