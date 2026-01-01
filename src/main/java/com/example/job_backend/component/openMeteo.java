package com.example.job_backend.component;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class openMeteo {

    @Value("${open-meteo_url_BLat}")
    private String url1;
    @Value("${open-meteo_url_MLat}")
    private String url2;
    @Value("${open-meteo_url_ALon}")
    private String url3;

    private RestTemplate rt = new RestTemplate();

    public WeatherData getWeather(double lat, double lon){
        String url = url1 + lat + url2 + lon + url3;

        Map<?,?> response = rt.getForObject(url, Map.class);
        Map<?,?> current = (Map<?, ?>) response.get("current");

        double temperature = ((Number) current.get("temperature_2m")).doubleValue();
        double humidity = ((Number) current.get("relative_humidity_2m")).doubleValue();
        double windSpeed = ((Number) current.get("wind_speed_10m")).doubleValue();

        return new WeatherData(temperature, humidity, windSpeed);



    }
    public record WeatherData(double temperature, double humidity, double windSpeed) {}

}


