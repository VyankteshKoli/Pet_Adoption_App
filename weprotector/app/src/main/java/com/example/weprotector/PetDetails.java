package com.example.weprotector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PetDetails extends AppCompatActivity {

    private TextView petName, petBreed, petAge, petDescription, adoptme, adoptme2;
    private TextView petVaccinated, petSterilization, petSize;
    private ImageView petImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petdetails);

        // Initialize views
        petName = findViewById(R.id.pet_name);
        petBreed = findViewById(R.id.pet_breed);
        petAge = findViewById(R.id.pet_age);
        petDescription = findViewById(R.id.pet_description);
        petImage = findViewById(R.id.pet_image);
        adoptme = findViewById(R.id.adopt_me);
        adoptme2 = findViewById(R.id.adopt_me2);
        petVaccinated = findViewById(R.id.pet_vaccinated);
        petSterilization = findViewById(R.id.pet_sterilization);
        petSize = findViewById(R.id.pet_size);

        // Get AddPetAdmin object from Intent
        AddPetAdmin pet = (AddPetAdmin) getIntent().getSerializableExtra("pet");

        if (pet != null) {
            petName.setText("Pet Name: " + pet.getName());
            petBreed.setText("Pet Breed: " + pet.getBreed());
            petAge.setText("Age: " + pet.getAge() + " years old");
            petDescription.setText(pet.getDescription());
            petVaccinated.setText("Vaccination: " + pet.getVaccination());
            petSterilization.setText("Sterilization: " + pet.getSterilization());
            petSize.setText("Size: " + pet.getSize());

            String imageUrl = pet.getImageUrl();

            if (imageUrl != null) {
                if (imageUrl.startsWith("http")) {
                    // Load image from Firebase Storage URL
                    Glide.with(this)
                            .load(imageUrl)
                            .placeholder(R.drawable.dog)
                            .into(petImage);
                } else {
                    // Decode Base64 string
                    try {
                        byte[] imageBytes = Base64.decode(imageUrl, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        petImage.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        petImage.setImageResource(R.drawable.dog);
                    }
                }
            } else {
                petImage.setImageResource(R.drawable.dog);
            }

            // Set adopt buttons text
            String adoptText = "Adopt " + pet.getName();
            adoptme.setText(adoptText);
            adoptme2.setText(adoptText);

            adoptme.setOnClickListener(v -> {
                Intent intent = new Intent(PetDetails.this, Adoption_Form.class);
                intent.putExtra("dogname", pet.getName());
                intent.putExtra("dogbreed", pet.getBreed());
                intent.putExtra("dogvaccinated", pet.getVaccination());
                intent.putExtra("dogage", String.valueOf(pet.getAge()));
                intent.putExtra("dogserialization", pet.getSterilization());
                startActivity(intent);
            });



        }
    }
}
