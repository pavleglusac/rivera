package com.tim20.rivera.repository;

import com.tim20.rivera.dto.AttendanceDTO;
import com.tim20.rivera.dto.IncomeDTO;
import com.tim20.rivera.dto.IncomeFrontDTO;
import com.tim20.rivera.model.*;
import lombok.NonNull;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.lang.NonNullApi;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByClientAndStartDateTimeAfter(Client client, LocalDateTime start);
    @Query("select r from Reservation r WHERE r.rentable.owner = ?1 and r.startDateTime > ?2")
    List<Reservation> findByRentableOwnerAndStartDateTime(Owner owner, LocalDateTime start);
    List<Reservation> findByRentableAndCancelledAndStartDateTimeIsAfter(Rentable rentable, Boolean cancelled, LocalDateTime dateTime);
    List<Reservation> findByRentable(Rentable rentable);
    List<Reservation> findByCancelled(Boolean cancelled);
    List<Reservation> findByStartDateTimeIsAfter(LocalDateTime dateTime);
    List<Reservation> findByRentableIdAndStartDateTimeBeforeAndEndDateTimeAfter(Integer id, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Reservation> findByRentableId(Integer id);
    List<Reservation> findByRentableOwnerUsername(String username);
    List<Reservation> findByRentableOwnerUsernameAndCancelledAndStartDateTimeAfterAndEndDateTimeBefore(String username, boolean cancelled, LocalDateTime startDateTime, LocalDateTime endDateTime);
    @Query("SELECT " +
            "   new com.tim20.rivera.dto.IncomeDTO(rentable, SUM(reservation.ownerIncomePercentage*reservation.price)) " +
            "FROM " +
            "    Reservation reservation inner join reservation.rentable rentable inner join rentable.owner owner where owner.username like ?1" +
            " and reservation.startDateTime >= ?2 and reservation.startDateTime < ?3 group by rentable")
    List<IncomeDTO> findIncome(String username, LocalDateTime startDateTime, LocalDateTime endDateTime);
    @Query(value = "SELECT   ren.name,ren.id,ren.owner_username,\n" +
            "             coalesce(SUM(res.owner_income_percentage*res.price),0)\n" +
            "            FROM\n" +
            "           reservation res right outer join cottage ren on ren.id=res.rentable_id\n" +
            "  and res.start_date_time >= ?1 and res.start_date_time < ?2 group by ren.name,ren.id",nativeQuery = true)
    List<Object[]> findCottageIncome(LocalDateTime startDateTime, LocalDateTime endDateTime);
    @Query(value = "SELECT   ren.name,ren.id,ren.owner_username,\n" +
            "             coalesce(SUM(res.owner_income_percentage*res.price),0)\n" +
            "            FROM\n" +
            "           reservation res right outer join boat ren on ren.id=res.rentable_id\n" +
            "  and res.start_date_time >= ?1 and res.start_date_time < ?2 group by ren.id",nativeQuery = true)
    List<Object[]> findBoatIncome(LocalDateTime startDateTime, LocalDateTime endDateTime);
    @Query(value = "SELECT   ren.name,ren.id,ren.owner_username,\n" +
            "             coalesce(SUM(res.owner_income_percentage*res.price),0)\n" +
            "            FROM\n" +
            "           reservation res right outer join adventure ren on ren.id=res.rentable_id\n" +
            "  and res.start_date_time >= ?1 and res.start_date_time < ?2 group by ren.id,ren.name",nativeQuery = true)
    List<Object[]> findAdventureIncome(LocalDateTime startDateTime, LocalDateTime endDateTime);
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
