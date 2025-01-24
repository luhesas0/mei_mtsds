package com.example.service;

import com.example.config.MapboxGeocodingResponse;
import com.example.dtos.RouteRequestDTO;
import com.example.dtos.RouteResponseDTO;
import com.example.dtos.RouteSummaryDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    private final WebClient webClient;

    @Value("${mapbox.api.url}")
    private String mapboxApiUrl;

    @Value("${mapbox.api.token}")
    private String mapboxApiToken;

    public RouteService(WebClient webClient) {
        this.webClient = webClient;
    }

    public RouteSummaryDTO calcularRota(RouteRequestDTO request) {
        try {
            // Geocode the origin and destination
            String origemCoordinates = geocodeAddress(request.getOrigin());
            String destinoCoordinates = geocodeAddress(request.getDestination());

            System.out.println("Origin Coordinates: " + origemCoordinates);
            System.out.println("Destination Coordinates: " + destinoCoordinates);

            // Construct the Directions API request
            String url = String.format("%s%s;%s?access_token=%s",
                    mapboxApiUrl,
                    origemCoordinates,
                    destinoCoordinates,
                    mapboxApiToken
            );

            System.out.println("Request URL: " + url);

            // Call the Directions API
            RouteResponseDTO response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(RouteResponseDTO.class)
                    .block();

            if (response == null || response.getRoutes() == null) {
                throw new RuntimeException("Nenhuma rota encontrada entre os locais fornecidos.");
            }

            return summarizeRoute(response);

        } catch (WebClientResponseException e) {
            if (e.getStatusCode().value() == 422) {
                throw new RuntimeException("Os endereços fornecidos não são válidos para a API do Mapbox.", e);
            }
            throw new RuntimeException("Erro ao chamar a API do Mapbox: " + e.getMessage(), e);
        }
    }

    private String geocodeAddress(String address) {
        // Format the address to include "Portugal" for better geocoding accuracy
        String encodedAddress = String.format("%s, Portugal", address).replace(" ", "%20");
        String geocodeUrl = String.format("https://api.mapbox.com/geocoding/v5/mapbox.places/%s.json?access_token=%s",
                encodedAddress,
                mapboxApiToken
        );

        // Fetch the response
        MapboxGeocodingResponse response = webClient.get()
                .uri(geocodeUrl)
                .retrieve()
                .bodyToMono(MapboxGeocodingResponse.class)
                .block();

        // Log the full geocoding response for debugging
        if (response == null || response.getFeatures().isEmpty()) {
            System.err.println("No geocoding results found for address: " + address);
            throw new RuntimeException("No geocoding results found for address: " + address);
        }

        System.out.println("Geocoding API Response (Full): " + response);

        // Extract the most relevant coordinates
        List<Double> center = response.getFeatures().get(0).getCenter();
        System.out.println("Extracted Coordinates (longitude,latitude): " + center);

        // Validate the coordinates
        if (center.size() != 2) {
            throw new RuntimeException("Invalid center coordinates for address: " + address);
        }

        // Return coordinates in the correct order: longitude,latitude
        return String.format("%s,%s", center.get(0), center.get(1));
    }


    public RouteSummaryDTO summarizeRoute(RouteResponseDTO response) {
        if (response == null || response.getRoutes() == null || response.getRoutes().isEmpty()) {
            throw new IllegalArgumentException("No route data available");
        }

        RouteResponseDTO.Route route = response.getRoutes().get(0); // First route
        double distanceKm = route.getDistance() / 1000.0; // Convert meters to kilometers
        double durationMinutes = route.getDuration() / 60.0; // Convert seconds to minutes

        List<String> legSummaries = new ArrayList<>();
        for (RouteResponseDTO.Leg leg : route.getLegs()) {
            legSummaries.add(leg.getSummary());
        }

        return new RouteSummaryDTO(
                distanceKm + " km",
                Math.round(durationMinutes) + " minutes",
                String.join(", ", legSummaries)
        );
    }

}


