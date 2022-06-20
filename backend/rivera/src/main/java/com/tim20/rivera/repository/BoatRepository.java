package com.tim20.rivera.repository;

import com.tim20.rivera.model.Boat;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Rentable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface BoatRepository extends JpaRepository<Boat, Integer> {
    List<Boat> findByOwnerUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Boat b where b.id = ?1")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Rentable findOneById(int rentableId);
}
