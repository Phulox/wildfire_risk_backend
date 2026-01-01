Wildfire Risk Map Backend (Spring Boot + PostgreSQL):

    Backend service for ingesting wildfire detections from NASA FIRMS (CSV), enriching each detection with Open-Meteo current weather, computing a simple wildfire risk score, storing results in PostgreSQL via Spring Data JPA, and exposing incidents as JSON for a frontend map.

    Fetches daily NASA FIRMS fire detections (CSV) and parses latitude/longitude + FRP (Fire Radiative Power).

    Calls Open-Meteo “current” weather for each detection (temperature, humidity, wind speed).

    Computes a risk score and flags incidents as HIGH risk if score ≥ 15.0.

    Persists incidents to PostgreSQL using JPA (IncidentModel).

    Scheduled ingestion runs automatically every 30 seconds while the server is running.

    Exposes REST endpoints for ingestion + frontend consumption.

    CORS configured to allow your frontend origin (e.g., Vite dev server).