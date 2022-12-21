package com.example.app.repository;

import com.example.app.model.Airport;
import com.example.app.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE person SET email=?2 WHERE person_id=?1", nativeQuery = true)
    void updatePersonEmail(Long personId, String newEmail);
}
