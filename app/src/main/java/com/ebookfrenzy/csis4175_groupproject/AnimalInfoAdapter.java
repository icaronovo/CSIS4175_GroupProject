package com.ebookfrenzy.csis4175_groupproject;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AnimalInfoAdapter extends RecyclerView.Adapter<AnimalInfoAdapter.ViewHolder> {
    private ArrayList<String> animals;

    public AnimalInfoAdapter(ArrayList animals) {
        this.animals = animals;
    }

    public ArrayList<String> getAnimals() {
        return animals;
    }

    private View.OnClickListener onItemClickListener; // Click listener for the items

    public void setOnItemClickListener(View.OnClickListener listener) {
        onItemClickListener = listener;
    }

    // Called when RecyclerView needs a new ViewHolder for creating a new cell
    @NonNull
    @Override
    public AnimalInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_cell, parent, false);
        return new AnimalInfoAdapter.ViewHolder(view);
    }


    // Binds data to view holder
    @Override
    public void onBindViewHolder(@NonNull AnimalInfoAdapter.ViewHolder holder, int position) {
        // Retrieve the AnimalSighting object at the given position
        String animal = animals.get(position);
        holder.animalName.setText(animal);

        holder.itemView.setOnClickListener(onItemClickListener);


    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    // Holds reference to the view of each cell
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView animalName;



        ViewHolder(View itemView) {
            super(itemView);
            animalName = itemView.findViewById(R.id.animal);
        }
    }

}
