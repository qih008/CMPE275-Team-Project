package com.example.cmpe275teambackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cmpe275teambackend.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}