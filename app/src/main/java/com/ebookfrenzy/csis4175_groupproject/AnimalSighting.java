package com.ebookfrenzy.csis4175_groupproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.type.DateTime;

public class AnimalSighting implements Parcelable {
    private String userID;
    private String animalType;
    private double latitude;
    private double longitude;
    private String description;
    private long dateTime;

    public AnimalSighting(){}

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


    protected AnimalSighting(Parcel in) {
        userID = in.readString();
        animalType = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        description = in.readString();
        dateTime = in.readLong();
    }

    public static final Creator<AnimalSighting> CREATOR = new Creator<AnimalSighting>() {
        @Override
        public AnimalSighting createFromParcel(Parcel in) {
            return new AnimalSighting(in);
        }

        @Override
        public AnimalSighting[] newArray(int size) {
            return new AnimalSighting[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userID);
        dest.writeString(animalType);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(description);
        dest.writeLong(dateTime);
    }
}
