package com.tim20.rivera.service;

import com.tim20.rivera.dto.ReservationReportDTO;
import com.tim20.rivera.model.ReservationReport;
import com.tim20.rivera.repository.ReservationReportRepository;
import com.tim20.rivera.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationReportService {

    @Autowired
    ReservationReportRepository reservationReportRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    EmailService emailService;

    public Integer fileReport(ReservationReportDTO reservationReportDTO) throws InterruptedException {
        ReservationReport reservationReport = new ReservationReport();
        reservationReport.setReservation(reservationRepository.findById(reservationReportDTO.getId()).get());
        reservationReport.setReservationReportType(reservationReportDTO.getReservationReportType());
        reservationReport.setSanction(reservationReportDTO.getSanction());
        reservationReport.setShowedUp(reservationReportDTO.getShowedUp());
        reservationReport.setText(reservationReportDTO.getText());
        reservationReport.setId(reservationReportDTO.getId());
        if(reservationReportDTO.getSanction()){
            emailService.sendReportAsync(reservationReportDTO);
        }
        reservationReportRepository.save(reservationReport);

        return reservationReportDTO.getId();
    }

    public ReservationReportDTO getById(Integer id) {
        return reservationReportToDTO(reservationReportRepository.getById(id));
    }

    public ReservationReportDTO reservationReportToDTO(ReservationReport reservationReport){
            ReservationReportDTO reservationReportDTO = new ReservationReportDTO();
            reservationReportDTO.setId(reservationReport.getId());
            reservationReportDTO.setReservationReportType(reservationReport.getReservationReportType());
            reservationReportDTO.setShowedUp(reservationReport.getShowedUp());
            reservationReportDTO.setSanction(reservationReport.getSanction());
            reservationReportDTO.setText(reservationReport.getText());
        return reservationReportDTO;
    }
}
