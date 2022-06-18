package com.tim20.rivera.repository;

import com.tim20.rivera.model.Rentable;
import com.tim20.rivera.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface RentableRepository extends JpaRepository<Rentable, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    @Query("select r from Rentable r where r.id = ?1")
    Rentable getByIdAndLock(int rentableId);
}
