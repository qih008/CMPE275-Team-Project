package com.example.cmpe275teambackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cmpe275teambackend.model.NB_Schedule;

@Repository
public interface NB_Repository extends JpaRepository<NB_Schedule, String> {

}