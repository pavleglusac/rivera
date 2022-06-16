package com.tim20.rivera.repository;

import com.tim20.rivera.model.Person;
import com.tim20.rivera.model.TerminationRequest;
import com.tim20.rivera.model.TerminationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TerminationRepository extends JpaRepository<TerminationRequest, Integer> {
    List<TerminationRequest> findByPerson(Person person);
    List<TerminationRequest> findAllByStatus(TerminationStatus status);
    List<TerminationRequest> findAllByPersonAndStatus(Person person, TerminationStatus status);
    TerminationRequest findById(int id);
}
