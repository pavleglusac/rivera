package com.tim20.rivera.services;

import com.tim20.rivera.dto.ReservationReportDTO;
import com.tim20.rivera.model.ReservationReportType;
import com.tim20.rivera.repository.ReservationReportRepository;
import com.tim20.rivera.repository.ReservationRepository;
import com.tim20.rivera.service.ReservationReportService;
import com.tim20.rivera.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.time.LocalDateTime;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ReservationReportServiceTest {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationReportService reservationReportService;

    @Autowired
    ReservationReportRepository reservationReportRepository;

    @Test
    @Transactional
    public void testFileReport(){
        reservationRepository.findAll();
        int numberOfReports = reservationReportRepository.findAll().size();
        ReservationReportDTO reservationReportDTO = new ReservationReportDTO();
        reservationReportDTO.setId(4);
        reservationReportDTO.setReservationReportType(ReservationReportType.REPORT);
        reservationReportDTO.setText("text");
        reservationReportDTO.setSanction(false);
        reservationReportDTO.setShowedUp(true);
        reservationReportDTO.setPosted(LocalDateTime.of(2022,5,5,12,12));
        try {
            reservationReportService.fileReport(reservationReportDTO);
        } catch (MessagingException e) {
            assert false;
        }
        assert reservationReportRepository.findById(4).isPresent() && reservationReportRepository.findAll().size() == numberOfReports + 1;
    }
}
