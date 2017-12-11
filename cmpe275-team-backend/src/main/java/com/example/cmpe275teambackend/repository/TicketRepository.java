package com.example.cmpe275teambackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cmpe275teambackend.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}