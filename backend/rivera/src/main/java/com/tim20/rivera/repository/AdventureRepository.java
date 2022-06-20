package com.tim20.rivera.repository;

import com.tim20.rivera.model.Adventure;
import com.tim20.rivera.model.Cottage;
import com.tim20.rivera.model.Person;
import com.tim20.rivera.model.Rentable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface AdventureRepository extends JpaRepository<Adventure, Integer> {
    List<Adventure> findByOwnerUsername(String username);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Adventure a where a.id = ?1")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Rentable findOneById(int rentableId);
}
