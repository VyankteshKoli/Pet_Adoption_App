package com.example.weprotector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

    private List<AddPetAdmin> petList;
    private OnItemClickListener onItemClickListener;

    public PetAdapter(List<AddPetAdmin> petList, OnItemClickListener listener) {
        this.petList = petList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        AddPetAdmin pet = petList.get(position);

        holder.petName.setText(pet.getName());
        holder.petBreed.setText(pet.getBreed());
        holder.petAge.setText(pet.getAge() + " years old");

        String imageUrl = pet.getImageUrl();

        if (imageUrl != null) {
            if (imageUrl.startsWith("http")) {
                // Load URL from Firebase using Glide
                Glide.with(holder.petImage.getContext())
                        .load(imageUrl)
                        .placeholder(R.drawable.dog)
                        .into(holder.petImage);
            } else {
                // Decode Base64 string
                try {
                    byte[] imageBytes = Base64.decode(imageUrl, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    holder.petImage.setImageBitmap(bitmap);
                } catch (Exception e) {
                    holder.petImage.setImageResource(R.drawable.dog);
                }
            }
        } else {
            holder.petImage.setImageResource(R.drawable.dog);
        }

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
        void onItemClick(AddPetAdmin pet);
    }
}
