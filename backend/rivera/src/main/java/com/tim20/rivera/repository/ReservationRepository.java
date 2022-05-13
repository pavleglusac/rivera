package com.tim20.rivera.repository;

import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Rentable;
import com.tim20.rivera.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByRentableAndCancelledAndStartDateTimeIsAfter(Rentable rentable, Boolean cancelled, LocalDateTime dateTime);
    List<Reservation> findByRentable(Rentable rentable);
    List<Reservation> findByCancelled(Boolean cancelled);
    List<Reservation> findByStartDateTimeIsAfter(LocalDateTime dateTime);
    List<ReservationRepository>  findByRentableId(String id);
}
