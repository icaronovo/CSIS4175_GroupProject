package com.ebookfrenzy.csis4175_groupproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class AnimalSightingAdapter extends RecyclerView.Adapter<AnimalSightingAdapter.ViewHolder> {

    private List<AnimalSighting> sightings;

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

        ViewHolder(View itemView) {
            super(itemView);
            animalTypeTextView = itemView.findViewById(R.id.animalTypeTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
