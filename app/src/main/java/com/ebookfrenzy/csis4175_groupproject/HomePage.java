package com.ebookfrenzy.csis4175_groupproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomePage extends AppCompatActivity {

    Button signOutButton;
    Button sightingsButton;
    Button registerSighting;
    Button animalInfoButton;
    Intent sendData;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    static final int REQUEST_CODE = 101;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        sendData = new Intent(HomePage.this, RegisterSighting.class);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();

        registerSighting = findViewById(R.id.registerSighting);
        signOutButton = findViewById(R.id.signOutButton);
        sightingsButton = findViewById(R.id.toSightings);
        animalInfoButton = findViewById(R.id.toAnimals);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePage.this, Login.class));
            }
        });

        sightingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, Sightings.class));
            }
        });

        animalInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, AnimalInfoList.class));
            }
        });

        registerSighting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();

                startActivity(sendData);
            }
        });
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                currentLocation = location;

                sendData.putExtra("Lat", currentLocation.getLatitude());
                sendData.putExtra("Lon", currentLocation.getLongitude());
                //sendData.putExtra("UID", UID);
                Toast.makeText(this, "Error finding your location.", Toast.LENGTH_LONG);

            } else {
                Toast.makeText(this, "Error finding your location.", Toast.LENGTH_LONG);
            }
        });
    }
}