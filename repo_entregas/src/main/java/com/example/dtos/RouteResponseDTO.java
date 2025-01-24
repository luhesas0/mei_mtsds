package com.example.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RouteResponseDTO {
    private List<Route> routes;

    @Getter
    @Setter
    public static class Route {
        private double distance; // Total distance in meters
        private double duration; // Total duration in seconds
        private String geometry; // Encoded polyline for the route
        private List<Leg> legs;  // List of legs in the route
    }

    @Getter
    @Setter
    public static class Leg {
        private String summary;    // Summary of the leg (e.g., main road)
        private List<Step> steps;  // Steps within the leg
    }

    @Getter
    @Setter
    public static class Step {
        private double distance; // Distance for this step in meters
        private double duration; // Duration for this step in seconds
        private String name;     // Name of the road/step
    }
}
