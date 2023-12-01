package com.ebookfrenzy.csis4175_groupproject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MySightings extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimalSightingAdapter adapter;

    //EXCLUIR ESSA LINHA ABAIXO E DESCOMENTAR A OUTRA
    String userId = "ljGBOI0qpMZPhubJB9jV2N7d9E32";
    //String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //////////////////////////////////////////////////////////////////////////

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

        getAllSightings(sightings -> {
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            adapter = new AnimalSightingAdapter(sightings);

            recyclerView.setAdapter(adapter);
        });
    }

    private void getAllSightings(Sightings.FirestoreCallback firestoreCallback) {
        List<AnimalSighting> sightings = new ArrayList<>();
        db.collection("sightings").whereEqualTo("user", userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int i = 0;
                for (QueryDocumentSnapshot document : task.getResult()) {
                    sightings.add(new AnimalSighting(document.getString("user"), document.getString("animal"),
                            document.getDouble("latitude"), document.getDouble("longitude"),
                            document.getString("description"), document.getLong("dateTime")));
                    Log.d("ArrayList size", String.valueOf(sightings.size()));
                    Log.d("Document info", sightings.get(i).getAnimalType());
                    Log.d("Document info", sightings.get(i).getDescription());
                    Log.d("Document info", String.valueOf(sightings.get(i).getLatitude()));
                    Log.d("Document info", String.valueOf(sightings.get(i).getLongitude()));
                    i++;
                    Log.d(TAG, document.getId() + " => " + document.getData());
                }
                firestoreCallback.onCallback(sightings); // Notify callback with the data
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public interface FirestoreCallback {
        void onCallback(List<AnimalSighting> sightings);
    }


}

