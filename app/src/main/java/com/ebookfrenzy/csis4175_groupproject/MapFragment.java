package com.ebookfrenzy.csis4175_groupproject;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private UiSettings mUiSettings;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Marker selectedMarker;
    private OnLocationSelectedListener locationSelectedListener;
    double clickedLatitude;
    double clickedLongitude;

    String user;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            locationSelectedListener = (OnLocationSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnLocationSelectedListener");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Log.d("MapFragment", "onMapReady called");
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        ArrayList<AnimalSighting> sightings = new ArrayList<>();
        List<HomeLocation> homeLocations = new ArrayList<>();
        // Get all sightings from the db
        db.collection("sightings").get()
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                sightings.add(new AnimalSighting(
                                        document.getString("user"),
                                        document.getString("animal"),
                                        document.getDouble("latitude"),
                                        document.getDouble("longitude"),
                                        document.getString("description"),
                                        document.getLong("dateTime")));
                                Log.d("FirebaseData", document.getId() + " => " + document.getData());
                            }
                            for (int i = 0; i < sightings.size(); i++) {
                                float markerColor;
                                if (sightings.get(i).getAnimalType().toLowerCase().contains("bear")) {
                                    markerColor = 359;
                                } else if (sightings.get(i).getAnimalType().toLowerCase().contains("eagle")) {
                                    markerColor = 300;
                                }else if (sightings.get(i).getAnimalType().toLowerCase().contains("deer")) {
                                    markerColor = 240;
                                }else if (sightings.get(i).getAnimalType().toLowerCase().contains("moose")) {
                                    markerColor = 180;
                                }else if (sightings.get(i).getAnimalType().toLowerCase().contains("coyote")) {
                                    markerColor = 120;
                                }else if (sightings.get(i).getAnimalType().toLowerCase().contains("fox")) {
                                    markerColor = 60;
                                } else {
                                    markerColor = 1;
                                }
                                mMap.addMarker(new MarkerOptions().position(new LatLng(sightings.get(i).getLatitude(), sightings.get(i).getLongitude())).title(sightings.get(i).getAnimalType()).icon(BitmapDescriptorFactory.defaultMarker(markerColor)));

                            }
                        } else {
                            Log.e("FirebaseError", "Error getting documents.", task.getException());
                        }
                    }
                });

        // Get current location
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();

        task.addOnCompleteListener(requireActivity(), new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Current location").icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location_icon)));
                    mMap.moveCamera(CameraUpdateFactory
                            .newCameraPosition(new CameraPosition
                                    .Builder()
                                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .zoom(13.0f)
                                    .build()));
                    Log.d("CurrentLocation Latitude", String.valueOf(location.getLatitude()));
                    Log.d("CurrentLocation Longitude", String.valueOf(location.getLongitude()));
                    Log.d("Sightings length", String.valueOf(sightings.size()));
                } else {
                    Log.e("LocationError", "Unable to get location");
                }
            }
        });

        db.collection("homeLocations").whereEqualTo("user", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {

                    homeLocations.add(new HomeLocation(document.getString("user"), Double.parseDouble(document.getString("latitude")), Double.parseDouble(document.getString("longitude"))));

                    for (HomeLocation a : homeLocations) {
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(a.getLatitude(), a.getLongitude())).title("Home")
                                .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.home_pin)));
                    }
                }
            }
        });

        // Allows the user to click the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Handle map click event
                handleMapClick(latLng);
            }
        });


    }

    private void handleMapClick(LatLng latLng) {
        Log.d("MapFragment", "Map clicked at: " + latLng.latitude + ", " + latLng.longitude);

        if (selectedMarker != null) {
            selectedMarker.remove();
        }

        selectedMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));

        if (locationSelectedListener != null) {
            locationSelectedListener.onLocationSelected(latLng.latitude, latLng.longitude);
        }
    }

    public interface OnLocationSelectedListener {
        void onLocationSelected(double latitude, double longitude);
    }


}
