package com.ebookfrenzy.csis4175_groupproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {

    Button signOutButton;
    Button sightingsButton;

    Button animalInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
//        Bundle mBundle = new Bundle();

//        ArrayList<String> currentLocation = new ArrayList<>();
//        currentLocation.add("49.2488");
//        currentLocation.add("-122.9805");

//        mBundle.putStringArrayList("coordinates", currentLocation);
//        final androidx.fragment.app.FragmentManager mFragmentManager = getSupportFragmentManager();
//        final androidx.fragment.app.FragmentTransaction homePageMapsFragmentTransaction
//                = mFragmentManager.beginTransaction();
//        final HomePageMapsFragment homePageMapsFragment = new HomePageMapsFragment();
//
//        homePageMapsFragment.setArguments(mBundle);
//        homePageMapsFragmentTransaction.add(R.id.fragment_container_view, homePageMapsFragment).commit();

        signOutButton = findViewById(R.id.signOutButton);
        sightingsButton = findViewById(R.id.toSightings);
        animalInfoButton = findViewById(R.id.animalInfoButton);
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


    }
}