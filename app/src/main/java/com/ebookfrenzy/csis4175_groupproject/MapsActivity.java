package com.ebookfrenzy.csis4175_groupproject;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ebookfrenzy.csis4175_groupproject.databinding.ActivityMapsBinding;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        AnimalSighting vancouver = new AnimalSighting("Black bear", 49.28, -123.12, "A big black bear");
        AnimalSighting surrey = new AnimalSighting("Black bear", 49.13, -122.84, "A big black bear");
        AnimalSighting burnaby = new AnimalSighting("Black bear", 49.24, -122.98, "A big black bear");

        ArrayList<AnimalSighting> animalSightings = new ArrayList<AnimalSighting>();
        animalSightings.add(vancouver);
        animalSightings.add(surrey);
        animalSightings.add(burnaby);

        for (AnimalSighting a : animalSightings) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(a.getLatitude(), a.getLongitude())).title(a.getAnimalType()));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(vancouver.getLatitude(), vancouver.getLongitude())));
    }
}