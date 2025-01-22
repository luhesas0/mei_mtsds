package com.example.config;

import java.util.List;

public class MapboxGeocodingResponse {

    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }



    public static class Feature {
        private List<Double> center;
        private String place_name;

        public List<Double> getCenter() {
            return center;
        }

        public void setCenter(List<Double> center) {
            this.center = center;
        }

        public String getPlace_name() {
            return place_name;
        }

        public void setPlace_name(String place_name) {
            this.place_name = place_name;
        }
    }


}