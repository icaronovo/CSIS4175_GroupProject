//package com.ebookfrenzy.csis4175_groupproject;
//
//import static android.content.ContentValues.TAG;
//
//import androidx.annotation.NonNull;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.Manifest;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.UiSettings;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentChange;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.Query;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import org.checkerframework.checker.units.qual.A;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MapFragment extends Fragment implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    static final int REQUEST_CODE = 101;
//    private UiSettings mUiSettings;
//    ArrayList<AnimalSighting> sightings = new ArrayList<>();
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//    public MapFragment() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_maps, container, false);
//
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
//
//        getCurrentLocation();
//        getAllSightings();
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        return view;
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mUiSettings = mMap.getUiSettings();
//        mUiSettings.setZoomControlsEnabled(true);
//
//        updateMapWithAllSightings();
//    }
//
//    private void getCurrentLocation() {
//        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
//            return;
//        }
//
//        Task<Location> task = fusedLocationProviderClient.getLastLocation();
//
//        task.addOnSuccessListener(location -> {
//            if (location != null) {
//                sightings.add(new AnimalSighting("", "", location.getLatitude(), location.getLatitude(), "Current Location", System.currentTimeMillis()));
//                Log.d("CurrentLocation Latitude", String.valueOf(location.getLatitude()));
//                Log.d("CurrentLocation Longitude", String.valueOf(location.getLongitude()));
//            }
//        });
//    }
//
////    private void updateMapWithLocation() {
////        if (mMap != null) {
////
////            mMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("Current location"));
////            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())));
////        }
////    }
//
//    private void getAllSightings() {
//
//        db.collection("sightings").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                sightings.add(new AnimalSighting(document.getString("user"), document.getString("animal"), document.getDouble("latitude"), document.getDouble("longitude"), document.getString("description"), document.getLong("dateTime")));
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
//
//                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
//                        }
//                    }
//                });
//    }
//
//    private void updateMapWithAllSightings() {
//        if (mMap != null) {
//            Log.d("Sightings Size", String.valueOf(sightings.size()));
//            for (AnimalSighting a : sightings) {
//                mMap.addMarker(new MarkerOptions().position(new LatLng(a.getLatitude(), a.getLongitude())).title(a.getAnimalType()));
//            }
//        }
//    }
//}
//
////    @Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container,
////                             Bundle savedInstanceState) {
////        View view = inflater.inflate(R.layout.fragment_maps, container, false);
////
////        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
////        getCurrentLocation();
////        getAllSightings();
////
////        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
////                .findFragmentById(R.id.map);
////        mapFragment.getMapAsync(this);
////
////        query.addSnapshotListener((value, error) -> {
////            if (error != null) {
////                // Handle error
////                return;
////            }
////            ArrayList<AnimalSighting> sightings = new ArrayList<>();
////
////            for (DocumentChange documentChange : value.getDocumentChanges()) {
////                switch (documentChange.getType()) {
////                    case ADDED:
////                        // Document added, handle accordingly
////                        ArrayList<AnimalSighting> newSightings = new ArrayList<>();
////                        QueryDocumentSnapshot document = documentChange.getDocument();
////                        newSightings.add(new AnimalSighting(document.getString("user"), document.getString("animal"), document.getDouble("latitude"), document.getDouble("longitude"), document.getString("description"), document.getLong("dateTime")));
////
////                        // Update your map with the new coordinates
////                        updateMapWithCoordinates(newSightings);
////                        break;
////                }
////            }
////        });
////
////        return view;
////    }
////
////    private void updateMapWithCoordinates(ArrayList<AnimalSighting> newSightings) {
////        if (mMap != null) {
////            for (AnimalSighting a : newSightings) {
////                mMap.addMarker(new MarkerOptions().position(new LatLng(a.getLatitude(), a.getLongitude())).title(a.getAnimalType()));
////            }
////        }
////    }
////
//
////
//
////
////    @Override
////    public void onMapReady(GoogleMap googleMap) {
////
////        mMap = googleMap;
////
////        if (currentLocation != null) {
////            //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("Current location"));
////            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 12.0f));
////        } else {
////            // Handle the case where the location is not available yet
////        }
////    }
////
////    private void getCurrentLocation() {
////        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
////                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
////            return;
////        }
////
////        Task<Location> task = fusedLocationProviderClient.getLastLocation();
////
////        task.addOnSuccessListener(location -> {
////            if (location != null) {
////                currentLocation = location;
////                updateMapWithLocation();
////            }
////        });
////    }
////
////    private void updateMapWithLocation() {
////        if (mMap != null) {
////
////            //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).title("Current location"));
////            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())));
////        }
////    }
////}
//

package com.ebookfrenzy.csis4175_groupproject;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private UiSettings mUiSettings;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Marker selectedMarker;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

//        getCurrentLocation();
//        getAllSightings();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MapFragment", "onMapReady called");
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        ArrayList<AnimalSighting> sightings = new ArrayList<>();

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
                            for (AnimalSighting a : sightings) {
                                mMap.addMarker(new MarkerOptions().position(new LatLng(a.getLatitude(), a.getLongitude())).title(a.getAnimalType()));
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

        // Clear the previous marker
        if (selectedMarker != null) {
            selectedMarker.remove();
        }

        // Add a new marker
        selectedMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));

        // You can also store the clicked coordinates for further use
        double clickedLatitude = latLng.latitude;
        double clickedLongitude = latLng.longitude;

        // Now, you can use these coordinates as needed, for example, store in Firebase
    }
}
