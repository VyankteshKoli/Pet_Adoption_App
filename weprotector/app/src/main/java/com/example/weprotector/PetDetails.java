package com.example.weprotector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PetDetails extends AppCompatActivity {

    private TextView petName, petBreed, petAge, petDescription, adoptme, adoptme2;
    private ImageView petImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.petdetails);

        petName = findViewById(R.id.pet_name);
        petBreed = findViewById(R.id.pet_breed);
        petAge = findViewById(R.id.pet_age);
        petImage = findViewById(R.id.pet_image);
        petDescription = findViewById(R.id.pet_description);
        adoptme = findViewById(R.id.adopt_me);
        adoptme2 = findViewById(R.id.adopt_me2);

        String name = getIntent().getStringExtra("pet_name");
        String breed = getIntent().getStringExtra("pet_breed");
        int age = getIntent().getIntExtra("pet_age", 0);
        int imageResId = getIntent().getIntExtra("pet_image", 0);

        petName.setText(String.format("Pet Name: %s", name));
        petBreed.setText(String.format("Pet Breed: %s", breed));
        petAge.setText(String.format("Age: %d years old", age));
        petImage.setImageResource(imageResId);

        String description = name + " is a peace-loving doggie! He is a pleasant guy to be around and is happy to humor anyone who wants to pet him.";
        petDescription.setText(description);

        String adoptpet = "Adopt " + name;
        adoptme.setText(adoptpet);
        adoptme2.setText(adoptpet);





        adoptme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetDetails.this, Adoption_Form.class);
                intent.putExtra("dogname", name);   // 👈 Send dog name
                intent.putExtra("dogbreed", breed); // 👈 Send dog breed
                startActivity(intent);
            }
        });

    }
}
