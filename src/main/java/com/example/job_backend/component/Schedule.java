package com.example.job_backend.component;

import com.example.job_backend.service.IncidentService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Schedule {
    private final IncidentService service;
    public Schedule(IncidentService service) {
        this.service = service;
    }

    @Scheduled(fixedDelay = 30000)
    public void scheduled() {
        service.createEntities();
    }
}
