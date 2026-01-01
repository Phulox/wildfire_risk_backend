package com.example.job_backend.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class nasaFirms {

    @Value("${nasa_firms_API}")
    private String templateAPI;
    private final RestTemplate rt = new RestTemplate();

    private String getNasaFirms() {
        LocalDateTime now = LocalDateTime.now();
        String today = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now);
        return templateAPI + today;
    }

    public String fetchCsv(){
        String temp = this.templateAPI;
        this.templateAPI = getNasaFirms();
        String data = rt.getForObject(templateAPI, String.class);
        this.templateAPI = temp;
        return data;
    }
}
