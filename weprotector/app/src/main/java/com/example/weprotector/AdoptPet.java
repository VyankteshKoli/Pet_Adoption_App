package com.example.weprotector;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdoptPet extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adoptpet);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<PetAdapterModel> petList = new ArrayList<>();

        petList.add(new PetAdapterModel("Lucky", "Golden Retriever", 2, R.drawable.lucky));
        petList.add(new PetAdapterModel("Tiger", "Siamese Cat", 3, R.drawable.tiger));
        petList.add(new PetAdapterModel("Pinky", "Siberian Husky", 2, R.drawable.pinky));
        petList.add(new PetAdapterModel("Romie", "Persian Cat", 4, R.drawable.romie));
        petList.add(new PetAdapterModel("Bouncer", "Labrador Retriever", 5, R.drawable.bouncer));
        petList.add(new PetAdapterModel("Jumper", "Bengal Cat", 3, R.drawable.jumper));
        petList.add(new PetAdapterModel("FiveStar", "Beagle", 4, R.drawable.fivestar));
        petList.add(new PetAdapterModel("DODO", "Alaskan Malamute", 3, R.drawable.dodo));
        petList.add(new PetAdapterModel("Brownie", "Bernese Mountain Dog", 4, R.drawable.brownie));
        petList.add(new PetAdapterModel("Gunda", "Corgi", 2, R.drawable.gunda));
        petList.add(new PetAdapterModel("Shadow", "German Shepherd", 6, R.drawable.shadow));

//        petList.add(new PetAdapterModel("Milo", "Ragdoll Cat", 3, R.drawable.milo));
//        petList.add(new PetAdapterModel("Bella", "Pomeranian", 2, R.drawable.bella));
//        petList.add(new PetAdapterModel("Max", "Chow Chow", 4, R.drawable.max));
//        petList.add(new PetAdapterModel("Luna", "Maine Coon", 5, R.drawable.luna));
//        petList.add(new PetAdapterModel("Charlie", "Dachshund", 3, R.drawable.charlie));
//        petList.add(new PetAdapterModel("Coco", "Sphynx Cat", 4, R.drawable.coco));
//        petList.add(new PetAdapterModel("Leo", "Great Dane", 5, R.drawable.leo));
//        petList.add(new PetAdapterModel("Lucy", "Scottish Fold", 3, R.drawable.lucy));
//        petList.add(new PetAdapterModel("Rocky", "Bulldog", 4, R.drawable.rocky));

        PetAdapter adapter = new PetAdapter(petList, new PetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PetAdapterModel pet) {

//                Toast.makeText(AdoptPet.this, "You Clicked on: " + pet.getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AdoptPet.this, PetDetails.class);
                intent.putExtra("pet_name", pet.getName());
                intent.putExtra("pet_breed", pet.getBreed());
                intent.putExtra("pet_age", pet.getAge());
                intent.putExtra("pet_image", pet.getImageResourceId());
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }
}


