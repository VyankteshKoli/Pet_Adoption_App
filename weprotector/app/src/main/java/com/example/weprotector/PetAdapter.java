package com.example.weprotector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private List<PetAdapterModel> petList;
    private OnItemClickListener onItemClickListener;

    public PetAdapter(List<PetAdapterModel> petList, OnItemClickListener onItemClickListener) {
        this.petList = petList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        PetAdapterModel pet = petList.get(position);
        holder.petImage.setImageResource(pet.getImageResourceId());
        holder.petName.setText(pet.getName());
        holder.petBreed.setText(pet.getBreed());
        holder.petAge.setText(String.format("%d years old", pet.getAge()));

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(pet));
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }
    public static class PetViewHolder extends RecyclerView.ViewHolder {
        ImageView petImage;
        TextView petName, petBreed, petAge;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petImage = itemView.findViewById(R.id.pet_image);
            petName = itemView.findViewById(R.id.pet_name);
            petBreed = itemView.findViewById(R.id.pet_breed);
            petAge = itemView.findViewById(R.id.pet_age);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PetAdapterModel pet);
    }
}
