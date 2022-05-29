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
            "   new com.tim20.rivera.dto.IncomeDTO(rentable, SUM(reservation.ownerIncomePercentage*reservation.price)) " +
            "FROM " +
            "    Reservation reservation inner join reservation.rentable rentable inner join rentable.owner owner where owner.username like ?1" +
            " and reservation.startDateTime >= ?2 and reservation.startDateTime < ?3 group by rentable")
    List<IncomeDTO> findIncome(String username, LocalDateTime startDateTime, LocalDateTime endDateTime);
    @Query(value = "SELECT   function('date_trunc', 'week',reservation.startDateTime), COUNT (reservation)\n" +
            "            FROM \n" +
            "    Reservation reservation inner join reservation.rentable rentable inner join rentable.owner owner where owner.username = ?1" +
            " and reservation.startDateTime >= ?2 and reservation.startDateTime < ?3 \n and reservation.cancelled= ?4 \n" +
            "      group by function('date_trunc', 'week',reservation.startDateTime) order by function('date_trunc','week',reservation.startDateTime)" +
            " ")
    List<Object[]> findWeeklyAttendance(String username, LocalDateTime startDateTime, LocalDateTime endDateTime, boolean cancelled, String type);
    @Query(value = "SELECT   function('date_trunc', 'month',reservation.startDateTime), COUNT (reservation)\n" +
            "            FROM \n" +
            "    Reservation reservation inner join reservation.rentable rentable inner join rentable.owner owner where owner.username = ?1" +
            " and reservation.startDateTime >= ?2 and reservation.startDateTime < ?3 \n and reservation.cancelled= ?4 \n" +
            "      group by function('date_trunc', 'month',reservation.startDateTime) order by function('date_trunc','month',reservation.startDateTime)" +
            " ")
    List<Object[]> findMonthlyAttendance(String username, LocalDateTime startDateTime, LocalDateTime endDateTime, boolean cancelled, String type);
    @Query(value = "SELECT   function('date_trunc', 'year',reservation.startDateTime), COUNT (reservation)\n" +
            "            FROM \n" +
            "    Reservation reservation inner join reservation.rentable rentable inner join rentable.owner owner where owner.username = ?1" +
            " and reservation.startDateTime >= ?2 and reservation.startDateTime < ?3 \n and reservation.cancelled= ?4 \n" +
            "      group by function('date_trunc', 'year',reservation.startDateTime) order by function('date_trunc','year',reservation.startDateTime)" +
            " ")
    List<Object[]> findYearlyAttendance(String username, LocalDateTime startDateTime, LocalDateTime endDateTime, boolean cancelled, String type);

    List<Reservation> findByClientUsername(String username);

    @Query("SELECT SUM((1-reservation.ownerIncomePercentage)*reservation.price) " +
            "FROM " +
            " Reservation reservation where reservation.startDateTime >= ?1 and reservation.startDateTime < ?2 ")
    Double findTotalSystemIncome(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
