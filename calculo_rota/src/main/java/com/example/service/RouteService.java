package com.example.service;

import com.example.config.MapboxGeocodingResponse;
import com.example.dtos.RouteRequestDTO;
import com.example.dtos.RouteResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    public RouteResponseDTO calcularRota(RouteRequestDTO request) {
        try {
            // Geocode the origin
            String origemCoordinates = geocodeAddress(request.getOrigin());
            String destinoCoordinates = geocodeAddress(request.getDestination());

            System.out.println("Origin Coordinates: " + origemCoordinates);
            System.out.println("Destination Coordinates: " + destinoCoordinates);

            // Use coordinates to call the Directions API
            String url = String.format("%s%s;%s?access_token=%s",
                    mapboxApiUrl,
                    origemCoordinates,
                    destinoCoordinates,
                    mapboxApiToken
            );

            System.out.println("Request URL: " + url);

            RouteResponseDTO response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(RouteResponseDTO.class)
                    .block();

            assert response != null;
            if (response.getRoutes() == null) {
                throw new RuntimeException("Nenhuma rota encontrada entre os locais fornecidos.");
            }

            return response;

        } catch (WebClientResponseException e) {
            if (e.getStatusCode().value() == 422) {
                throw new RuntimeException("Os endereços fornecidos não são válidos para a API do Mapbox.", e);
            }
            throw new RuntimeException("Erro ao chamar a API do Mapbox: " + e.getMessage(), e);
        }
    }

    // Helper method to geocode addresses
    private String geocodeAddress(String address) {
        String encodedAddress = address.replace(" ", "%20");
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

        if (response == null || response.getFeatures().isEmpty()) {
            throw new RuntimeException("No geocoding results found for address: " + address);
        }

        // Log the entire response for debugging
        System.out.println("Geocoding API Response: " + response);

        // Extract the most relevant coordinates
        List<Double> center = response.getFeatures().get(0).getCenter();
        if (center.size() != 2) {
            throw new RuntimeException("Invalid center coordinates for address: " + address);
        }

        return String.format("%s,%s", center.get(0), center.get(1));
    }
}


