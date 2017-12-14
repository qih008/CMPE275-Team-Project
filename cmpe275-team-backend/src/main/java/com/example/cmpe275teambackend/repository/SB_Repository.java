package com.example.cmpe275teambackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cmpe275teambackend.model.SB_Schedule;

@Repository
public interface SB_Repository extends JpaRepository<SB_Schedule, String> {

}