package com.ebookfrenzy.csis4175_groupproject;

import com.google.type.DateTime;

public class AnimalSighting {
    private String userID;
    private String animalType;
    private double latitude;
    private double longitude;
    private String description;
    private long dateTime;

    public AnimalSighting(String userID, String animalType, double latitude, double longitude, String description, long dateTime) {
        this.animalType = animalType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.dateTime = dateTime;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }



}
