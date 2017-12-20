package com.example.cmpe275teambackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cmpe275teambackend.model.SystemReport;

@Repository
public interface SystemReportRepository extends JpaRepository<SystemReport, String> {

}