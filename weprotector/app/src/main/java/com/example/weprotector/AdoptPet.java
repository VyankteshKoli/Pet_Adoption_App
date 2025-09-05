package com.example.weprotector;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdoptPet extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<AddPetAdmin> petList;
    private PetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adoptpet);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        petList = new ArrayList<>();
        adapter = new PetAdapter(petList, pet -> {
            Intent intent = new Intent(AdoptPet.this, PetDetails.class);
            intent.putExtra("pet", pet); // pass AddPetAdmin object
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
        loadPetsFromFirebase();
    }

    private void loadPetsFromFirebase() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Pets");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                petList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    AddPetAdmin pet = data.getValue(AddPetAdmin.class);
                    if (pet != null) {
                        petList.add(pet);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdoptPet.this, "Failed to load pets: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
