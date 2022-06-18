package com.tim20.rivera.controller;

import com.tim20.rivera.dto.ReservationReportDTO;
import com.tim20.rivera.service.ReservationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/reservationReport")
public class ReservationReportController {

    @Autowired
    ReservationReportService reservationReportService;

    @PostMapping(path = "file-report")
    public ResponseEntity<String> fileReport(ReservationReportDTO reservationReportDTO) throws MessagingException {
        reservationReportService.fileReport(reservationReportDTO);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping(path = "get-report")
    public ReservationReportDTO getDTO(@RequestParam("id") Integer id) {
        return reservationReportService.getById(id);
    }
}
