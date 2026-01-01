package com.example.job_backend.model.repository;

import com.example.job_backend.model.entity.IncidentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentRepository extends JpaRepository<IncidentModel, Long> {
}
