package com.ebookfrenzy.csis4175_groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class AnimalInfoList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimalInfoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_info_list);

        // Initialize the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the title in the ActionBar
        getSupportActionBar().setTitle("Wild Life Information");

        // Enable the back button in the ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.animalRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> animals = new ArrayList<>();
        animals.add("Grizzly Bear");
        animals.add("Black Bear");
        animals.add("Bald Eagle");
        animals.add("Moose");
        animals.add("Cougar");
        animals.add("Grey Wolf");

        adapter = new AnimalInfoAdapter(animals);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}