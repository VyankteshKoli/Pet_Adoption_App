package com.example.weprotector;

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

public class AdoptionRequestAdmin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdoptionRequestAdapter adapter;
    private List<AdoptionRequest> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adoption_request_admin);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestList = new ArrayList<>();
        adapter = new AdoptionRequestAdapter(this, requestList);
        recyclerView.setAdapter(adapter);

        fetchAdoptionRequests();
    }

    private void fetchAdoptionRequests() {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("adoptionRequests");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestList.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String requestId = ds.getKey();

                    String petName = ds.child("petInfo").child("dogName").getValue(String.class);
                    String breed = ds.child("petInfo").child("breed").getValue(String.class);
                    String age = ds.child("petInfo").child("age").getValue(String.class);

                    String adopterName = ds.child("adopterInfo").child("name").getValue(String.class);
                    String adopterPhone = ds.child("adopterInfo").child("phone").getValue(String.class);
                    String adopterEmail = ds.child("adopterInfo").child("email").getValue(String.class);

                    String status = ds.child("status").getValue(String.class);

                    AdoptionRequest request = new AdoptionRequest(
                            requestId != null ? requestId : "",
                            petName != null ? petName : "",
                            breed != null ? breed : "",
                            age != null ? age : "",
                            adopterName != null ? adopterName : "",
                            adopterPhone != null ? adopterPhone : "",
                            adopterEmail != null ? adopterEmail : "",
                            status != null ? status : "pending"
                    );

                    requestList.add(request);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdoptionRequestAdmin.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
