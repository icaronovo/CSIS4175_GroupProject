package com.ebookfrenzy.csis4175_groupproject;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class AnimalSightingAdapter extends RecyclerView.Adapter<AnimalSightingAdapter.ViewHolder> {

    private static List<AnimalSighting> sightings;

    public AnimalSightingAdapter(List<AnimalSighting> sightings) {
        this.sightings = sightings;
    }

    // Called when RecyclerView needs a new ViewHolder for creating a new cell
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sighting_cell, parent, false);
        return new ViewHolder(view);
    }

    // Binds data to view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Retrieve the AnimalSighting object at the given position
        AnimalSighting sighting = sightings.get(position);
        holder.animalTypeTextView.setText(sighting.getAnimalType());
        holder.locationTextView.setText(String.format(Locale.getDefault(), "(%.2f, %.2f)", sighting.getLatitude(), sighting.getLongitude()));
        holder.descriptionTextView.setText(sighting.getDescription());
    }

    @Override
    public int getItemCount() {
        return sightings.size();
    }

    // Holds reference to the view of each cell
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView animalTypeTextView;
        TextView locationTextView;
        TextView descriptionTextView;
        Button showInMap;

        ViewHolder(View itemView) {
            super(itemView);
            animalTypeTextView = itemView.findViewById(R.id.animalTypeTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            showInMap = itemView.findViewById(R.id.sightingButton);

            showInMap.setOnClickListener(view -> {
                // Retrieve the AnimalSighting object at the clicked position
                AnimalSighting sighting = sightings.get(getAdapterPosition());

                // Create an intent to start the MapPopupActivity
                Intent intent = new Intent(itemView.getContext(), PopUpActivity.class);
                intent.putExtra("animalSighting", sighting);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
