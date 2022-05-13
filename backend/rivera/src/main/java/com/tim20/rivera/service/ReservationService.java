package com.tim20.rivera.service;

import com.tim20.rivera.dto.ReservationDTO;
import com.tim20.rivera.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<ReservationDTO> getReservationsByOwner() {
        return null;
    }
}
