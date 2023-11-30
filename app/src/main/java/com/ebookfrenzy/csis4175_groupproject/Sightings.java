package com.ebookfrenzy.csis4175_groupproject;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Sightings extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimalSightingAdapter adapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<AnimalSighting> sightings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sightings);

        // Initialize the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the title in the ActionBar
        getSupportActionBar().setTitle("Sightings");

        // Enable the back button in the ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sightings = getAllSightings();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AnimalSightingAdapter(sightings);

        recyclerView.setAdapter(adapter);
    }



    private List<AnimalSighting> getAllSightings() {
        sightings = new ArrayList<>();
        db.collection("sightings").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        sightings.add(new AnimalSighting(document.getString("user"), document.getString("animal"), document.getDouble("latitude"), document.getDouble("longitude"), document.getString("description"), document.getLong("dateTime")));
                        Log.d("ArrayList size", String.valueOf(sightings.size()));
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
        Log.d("ArrayList size", String.valueOf(sightings.size()));
        return sightings;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private List<AnimalSighting> createDummyData() {
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

        return dummyData;
    }
}