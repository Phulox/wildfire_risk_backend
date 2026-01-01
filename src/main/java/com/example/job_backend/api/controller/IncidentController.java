package com.example.job_backend.api.controller;

import com.example.job_backend.DTO.IncidentDTO;

import com.example.job_backend.service.IncidentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class IncidentController {
    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }
    @GetMapping
    public List<IncidentDTO> getIncidents(){
        return incidentService.getIncidents();
    }

    @PostMapping("/ingest")
    public void injest(){
        incidentService.createEntities();
    }


}
