package com.example.weprotector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PetAccessories extends AppCompatActivity {

    CardView cardDogCollar, cardDogShelter,cardDogFood, cardBodyWash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_accesories);

        cardDogCollar = findViewById(R.id.cardDogCollar);
        cardDogShelter = findViewById(R.id.cardDogShelter);
        cardDogFood = findViewById(R.id.cardDogFood);
        cardBodyWash = findViewById(R.id.cardBodyWash);

        cardDogCollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetAccessories.this, DogCollars.class);
                startActivity(intent);
            }
        });

        cardDogShelter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetAccessories.this, DogShelter.class);
                startActivity(intent);
            }
        });

        cardDogFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetAccessories.this, DogShelter.class);
                startActivity(intent);
            }
        });

        cardBodyWash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PetAccessories.this, DogShelter.class);
                startActivity(intent);
            }
        });
    }
}
