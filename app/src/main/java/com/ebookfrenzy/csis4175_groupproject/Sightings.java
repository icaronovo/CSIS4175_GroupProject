package com.ebookfrenzy.csis4175_groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Sightings extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimalSightingAdapter adapter;

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

        List<AnimalSighting> dummyData = createDummyData();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AnimalSightingAdapter(dummyData);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private List<AnimalSighting> createDummyData() {
        List<AnimalSighting> dummyData = new ArrayList<>();
        dummyData.add(new AnimalSighting("Bear", 40.7128, -74.0060, "Saw a bear near the river."));
        dummyData.add(new AnimalSighting("Moose", 36.7783, -119.4179, "Moose spotted in the forest."));
        dummyData.add(new AnimalSighting("Deer", 34.0522, -118.2437, "Observed a group of deer grazing."));
        dummyData.add(new AnimalSighting("Eagle", 41.8781, -87.6298, "Saw an eagle flying in the sky."));
        dummyData.add(new AnimalSighting("Fox", 37.7749, -122.4194, "Fox seen near the hiking trail."));
        dummyData.add(new AnimalSighting("Owl", 51.5074, -0.1278, "Spotted an owl in a tree at night."));
        dummyData.add(new AnimalSighting("Raccoon", 45.4215, -75.6993, "Encountered a raccoon near the park."));
        dummyData.add(new AnimalSighting("Squirrel", 34.0522, -118.2437, "Friendly squirrel approached for food."));
        dummyData.add(new AnimalSighting("Coyote", 37.7749, -122.4194, "Coyote observed in the open field."));
        dummyData.add(new AnimalSighting("Hawk", 40.7128, -74.0060, "Hawk soaring high above."));
        dummyData.add(new AnimalSighting("Bear", 42.3601, -71.0589, "A majestic brown bear spotted near a tranquil river. The bear was peacefully fishing for salmon, showcasing the beauty of wildlife in its natural habitat. This sighting left a lasting impression on the observer."));
        dummyData.add(new AnimalSighting("Moose", 44.0682, -114.7420, "Encountered a moose in the dense pine forest. Its antlers were remarkable, and it moved gracefully through the trees. A serene moment capturing the essence of the wilderness."));
        dummyData.add(new AnimalSighting("Eagle", 37.7749, -122.4194, "Witnessed a magnificent bald eagle soaring high above the cliffs. Its wingspan was awe-inspiring, a symbol of freedom and strength. An unforgettable sight amidst the breathtaking landscapes."));
        dummyData.add(new AnimalSighting("Fox", 48.8566, 2.3522, "Spotted a playful fox near a meadow. The orange fur stood out against the green grass, and the fox exhibited curious behavior, making it a delightful and heartwarming encounter."));
        dummyData.add(new AnimalSighting("Deer", 36.7783, -119.4179, "A family of deer grazing in a sunlit clearing. The fawns were adorable, and the scene captured the tranquility of the forest. Nature's beauty at its finest."));

        return dummyData;
    }
}