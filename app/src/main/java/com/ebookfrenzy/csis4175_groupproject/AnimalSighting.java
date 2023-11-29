package com.ebookfrenzy.csis4175_groupproject;

public class AnimalSighting {
    private String animalType;
    private double latitude;
    private double longitude;
    private String description;

    public AnimalSighting(String animalType, double latitude, double longitude, String description) {
        this.animalType = animalType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
