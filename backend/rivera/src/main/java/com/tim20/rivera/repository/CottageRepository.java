package com.tim20.rivera.repository;

import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Rentable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface CottageRepository  extends JpaRepository<Cottage, Integer> {
    List<Cottage> findByOwnerUsername(String username);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Cottage c where c.id = ?1")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Rentable findOneById(int rentableId);
}
