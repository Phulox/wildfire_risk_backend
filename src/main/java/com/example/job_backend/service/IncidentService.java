package com.example.job_backend.service;

import com.example.job_backend.DTO.IncidentDTO;
import com.example.job_backend.component.nasaFirms;
import com.example.job_backend.component.openMeteo;
import com.example.job_backend.model.entity.IncidentModel;
import com.example.job_backend.model.repository.IncidentRepository;
import com.example.job_backend.component.openMeteo.WeatherData;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncidentService {
    private final nasaFirms NasaFirmsClient;

    private final openMeteo OpenMeteoClient;

    private final IncidentRepository repository;

    public IncidentService(nasaFirms nasaFirms, IncidentRepository repository, openMeteo openMeteo) {
        this.NasaFirmsClient = nasaFirms;
        this.repository = repository;
        this.OpenMeteoClient = openMeteo;
    }

    @PostMapping("/ingest")
    public void createEntities() {
        String dataNF = NasaFirmsClient.fetchCsv();
        String[] lines = dataNF.split("\n");

        //Skipping headers
        for(int i =1; i < lines.length; i++){
            if (lines[i].isBlank()) continue;

            //ex: [13.2,-123,....,8.6,N]
            String[] cols = lines[i].split(",");
            //Missing data detected, skip line
            if (cols.length < 13) continue;

            double lat = Double.parseDouble(cols[0]);
            double lon = Double.parseDouble(cols[1]);
            double frp = Double.parseDouble(cols[12]);

            WeatherData weatherData = OpenMeteoClient.getWeather(lat,lon);

            //Calculate Risk factor
            double currentRisk = computeRisk(frp, weatherData.windSpeed(),  weatherData.temperature(), weatherData.humidity());
            boolean isHighRisk = isHighRisk(currentRisk);

            //Creating Entity with data received from Open Meteo and Nasa Firms
            IncidentModel incidentModel = new IncidentModel();
            incidentModel.setLat(lat);
            incidentModel.setLon(lon);
            incidentModel.setFrp(frp);
            incidentModel.setHumidity(weatherData.humidity());
            incidentModel.setTemperature(weatherData.temperature());
            incidentModel.setWindSpeed(weatherData.windSpeed());
            incidentModel.setRisk(isHighRisk);

            repository.save(incidentModel);
        }
    }

   public List<IncidentDTO> getIncidents(){
        List<IncidentModel> entities = repository.findAll();
        List<IncidentDTO> dtos = new ArrayList<>();
        for(IncidentModel entity : entities){
            IncidentDTO incidentDTO = new IncidentDTO(entity.getLat(), entity.getLon(),
                                        entity.getIsRisk());
            dtos.add(incidentDTO);

        }
        return dtos;
   }

    private double computeRisk(double frp, double windSpeed, double temperature, double humidity) {
        return frp * (1 + windSpeed / 10.0) * (1 + temperature / 40.0) * (1 - humidity / 100.0);
    }
    private boolean isHighRisk(double currentRisk) {
        return currentRisk >= 15.0;
    }
}
