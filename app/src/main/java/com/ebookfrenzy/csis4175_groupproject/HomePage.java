package com.ebookfrenzy.csis4175_groupproject;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
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


public class HomePage extends AppCompatActivity implements MapFragment.OnLocationSelectedListener {

    Button signOutButton;
    Button sightingsButton;
    Button registerSighting;
    Button animalInfoButton;
    Button mySightingsButton;
    Button setHomeLocation;
    Intent sendData;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    static final int REQUEST_CODE = 101;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Double clickedLatitude;
    Double clickedLongitude;
    String user;
    ImageView overlay;

    ScrollView scrollView;

    @SuppressLint("ClickableViewAccessibility")
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
        mySightingsButton = findViewById(R.id.toMySightings);
        setHomeLocation = findViewById(R.id.registerHomeLocation);
        overlay = findViewById(R.id.imageTransparent);
        scrollView = findViewById(R.id.scroll);

        overlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scrollView.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scrollView.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scrollView.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addDataToDatabase();

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

        mySightingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePage.this, MySightings.class));
            }
        });

        registerSighting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickedLatitude != null) {
                    sendData.putExtra("Lat", clickedLatitude);
                    sendData.putExtra("Lon", clickedLongitude);
                } else {
                    getCurrentLocation();
                }

                startActivity(sendData);
            }
        });

        setHomeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //user = "ljGBOI0qpMZPhubJB9jV2N7d9E32";

                if (clickedLatitude == null || clickedLongitude == null) {
                    Toast.makeText(HomePage.this, "Please select a place on the map", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Map<String, Object> homeLocation = new HashMap<>();
                    homeLocation.put("user", user);
                    homeLocation.put("latitude", clickedLatitude.toString());
                    homeLocation.put("longitude", clickedLongitude.toString());

                    db.collection("homeLocations").add(homeLocation).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
                }
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
            } else {
            }
        });
    }

    public void addDataToDatabase () {

        List<AnimalSighting> dummyData = new ArrayList<>();
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Bear", 40.7128, -74.0060, "Saw a bear near the river.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Moose", 36.7783, -119.4179, "Moose spotted in the forest.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Deer", 34.0522, -118.2437, "Observed a group of deer grazing.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Eagle", 41.8781, -87.6298, "Saw an eagle flying in the sky.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Fox", 37.7749, -122.4194, "Fox seen near the hiking trail.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Owl", 51.5074, -0.1278, "Spotted an owl in a tree at night.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Raccoon", 45.4215, -75.6993, "Encountered a raccoon near the park.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Squirrel", 34.0522, -118.2437, "Friendly squirrel approached for food.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Coyote", 37.7749, -122.4194, "Coyote observed in the open field.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Hawk", 40.7128, -74.0060, "Hawk soaring high above.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Bear", 42.3601, -71.0589, "A majestic brown bear spotted near a tranquil river. The bear was peacefully fishing for salmon, showcasing the beauty of wildlife in its natural habitat. This sighting left a lasting impression on the observer.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Moose", 44.0682, -114.7420, "Encountered a moose in the dense pine forest. Its antlers were remarkable, and it moved gracefully through the trees. A serene moment capturing the essence of the wilderness.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Eagle", 37.7749, -122.4194, "Witnessed a magnificent bald eagle soaring high above the cliffs. Its wingspan was awe-inspiring, a symbol of freedom and strength. An unforgettable sight amidst the breathtaking landscapes.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Fox", 48.8566, 2.3522, "Spotted a playful fox near a meadow. The orange fur stood out against the green grass, and the fox exhibited curious behavior, making it a delightful and heartwarming encounter.", System.currentTimeMillis()));
        dummyData.add(new AnimalSighting("ljGBOI0qpMZPhubJB9jV2N7d9E32", "Deer", 36.7783, -119.4179, "A family of deer grazing in a sunlit clearing. The fawns were adorable, and the scene captured the tranquility of the forest. Nature's beauty at its finest.", System.currentTimeMillis()));
        for (AnimalSighting a : dummyData) {

            Map<String, Object> sighting = new HashMap<>();
            sighting.put("user", a.getUserID());
            sighting.put("latitude", a.getLatitude());
            sighting.put("longitude", a.getLongitude());
            sighting.put("animal", a.getAnimalType());
            sighting.put("description", a.getDescription());
            sighting.put("dateTime", a.getDateTime());

            db.collection("sightings").add(sighting)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }

    @Override
    public void onLocationSelected(double latitude, double longitude) {
        clickedLatitude = latitude;
        clickedLongitude = longitude;
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }




}