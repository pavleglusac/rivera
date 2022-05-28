package com.tim20.rivera.repository;

import com.tim20.rivera.dto.AttendanceDTO;
import com.tim20.rivera.dto.IncomeDTO;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Owner;
import com.tim20.rivera.model.Rentable;
import com.tim20.rivera.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByRentableAndCancelledAndStartDateTimeIsAfter(Rentable rentable, Boolean cancelled, LocalDateTime dateTime);
    List<Reservation> findByRentable(Rentable rentable);
    List<Reservation> findByCancelled(Boolean cancelled);
    List<Reservation> findByStartDateTimeIsAfter(LocalDateTime dateTime);
    List<ReservationRepository>  findByRentableId(String id);
    List<Reservation> findByRentableOwnerUsername(String username);
    List<Reservation> findByRentableOwnerUsernameAndCancelledAndStartDateTimeAfterAndEndDateTimeBefore(String username, boolean cancelled, LocalDateTime startDateTime, LocalDateTime endDateTime);
    @Query("SELECT " +
            "    new com.tim20.rivera.dto.IncomeDTO(rentable, SUM(reservation.price)) " +
            "FROM " +
            "    Reservation reservation inner join reservation.rentable rentable inner join rentable.owner owner where owner.username = ?1" +
            " and reservation.startDateTime >= ?2 and reservation.startDateTime < ?3 group by rentable")
    List<IncomeDTO> findIncome(String username, LocalDateTime startDateTime, LocalDateTime endDateTime);
    @Query(value = "SELECT   date_trunc('week',res.start_date_time), COUNT (date_trunc('week',res.start_date_time))\n" +
            "            FROM \n" +
            "                reservation as res  inner join adventure as ren on res.rentable_id=ren.id\n" +
            "              inner join fishing_instructor as o on ren.owner_username=o.username WHERE o.username= ?1 \n" +
            "             and res.start_date_time>= ?2 and res.start_date_time<  ?3 and res.cancelled= ?4 \n" +
            "             group by date_trunc('week',res.start_date_time) order by date_trunc('week',res.start_date_time) ",nativeQuery = true)
    Map<Timestamp,Integer> findAttendance(String username, LocalDateTime startDateTime, LocalDateTime endDateTime, boolean cancelled, String type);
    List<Reservation> findByClientUsername(String username);
}
