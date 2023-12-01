package com.ebookfrenzy.csis4175_groupproject;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterSighting extends Activity {
    private FirebaseAuth mAuth;
    EditText animalTextEdit, descriptionTextEdit;
    Button submit;
    String user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView latitude, longitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_register_sighting);
        Intent intent = getIntent();
        Double currentLatitude = intent.getDoubleExtra("Lat", 0);
        Double currentLongitude = intent.getDoubleExtra("Lon", 0);
        //String UID = intent.getStringExtra("UID");

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        Log.d("Latitude", String.valueOf(currentLatitude));
        Log.d("Longitude", String.valueOf(currentLongitude));

        latitude.setText(currentLatitude.toString());
        longitude.setText(currentLongitude.toString());

        animalTextEdit = findViewById(R.id.animalTextEdit);
        descriptionTextEdit = findViewById(R.id.descriptionTextEdit);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();

                // DESCOMENTAR NO FUTURO////////////////////////////////////////////////////////////////////////////////
//                user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                // DESCOMENTAR NO FUTURO////////////////////////////////////////////////////////////////////////////////


                user = "ljGBOI0qpMZPhubJB9jV2N7d9E32";

                Map<String, Object> sighting = new HashMap<>();
                sighting.put("user", user);
                sighting.put("latitude", currentLatitude);
                sighting.put("longitude", currentLongitude);
                sighting.put("animal", animalTextEdit.getText().toString());
                sighting.put("description", descriptionTextEdit.getText().toString());
                sighting.put("dateTime", System.currentTimeMillis());

                db.collection("sightings").add(sighting)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                animalTextEdit.setText("");
                                descriptionTextEdit.setText("");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                startActivity(new Intent(RegisterSighting.this, HomePage.class));
            }
        });


    }
}
