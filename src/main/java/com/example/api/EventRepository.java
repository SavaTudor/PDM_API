package com.example.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("select e from Event e where e.name = ?1 and e.location=?2 and e.time=?3")
    ArrayList<Event> findByProperties(String name, String location, String time);
}
