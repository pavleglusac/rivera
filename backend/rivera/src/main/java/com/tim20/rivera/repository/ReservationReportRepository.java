package com.tim20.rivera.repository;

import com.tim20.rivera.model.Rentable;
import com.tim20.rivera.model.ReservationReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationReportRepository  extends JpaRepository<ReservationReport, Integer> {
    public List<ReservationReport> findAllByResolved(boolean resolved);
}
