package com.example.weprotector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RequestConformation extends AppCompatActivity {

    private TextView tvCongrats, tvAccepted, tvPetName, tvPetBreed, tvPetAge, tvAdminContact;
    private ImageView ivPetImage;

    private final String requestId = "OZNy61coj-fLzzqJWIx"; // adoption request ID
    private final String adminId = "FlpLlUJxZPWB3EQCqbpKod3Run32"; // admin ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_conformation);

        // Initialize views
        tvCongrats = findViewById(R.id.tvCongrats);
        tvAccepted = findViewById(R.id.tvAccepted);
        tvPetName = findViewById(R.id.tvPetName);
        tvPetBreed = findViewById(R.id.tvPetBreed);
        tvPetAge = findViewById(R.id.tvPetAge);
        tvAdminContact = findViewById(R.id.tvAdminContact);
        ivPetImage = findViewById(R.id.ivPetImage);

        loadAdoptionRequest();
    }

    private void loadAdoptionRequest() {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("adoptionRequests")
                .child(requestId);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    tvAccepted.setText("Adoption request not found.");
                    return;
                }

                String status = snapshot.child("status").getValue(String.class);
                if (!"accepted".equalsIgnoreCase(status)) {
                    tvAccepted.setText("Your adoption request is not yet accepted.");
                    return;
                }

                // Get pet info from adoptionRequests
                String dogName = snapshot.child("petInfo/dogName").getValue(String.class);
                String breed = snapshot.child("petInfo/breed").getValue(String.class);
                String age = snapshot.child("petInfo/age").getValue(String.class);

                tvPetName.setText("Pet Name: " + dogName);
                tvPetBreed.setText("Breed: " + breed);
                tvPetAge.setText("Age: " + age);

                // Load pet image from Pets node by matching dogName
                DatabaseReference petsRef = FirebaseDatabase.getInstance().getReference("Pets");
                petsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot petsSnapshot) {
                        boolean found = false;
                        for (DataSnapshot petSnap : petsSnapshot.getChildren()) {
                            String petName = petSnap.child("name").getValue(String.class);
                            if (petName != null && petName.equalsIgnoreCase(dogName)) {
                                String imageUrl = petSnap.child("imageUrl").getValue(String.class);

                                if (imageUrl != null && !imageUrl.isEmpty()) {
                                    if (imageUrl.startsWith("http")) {
                                        Glide.with(RequestConformation.this)
                                                .load(imageUrl)
                                                .placeholder(R.drawable.dog)
                                                .into(ivPetImage);
                                    } else {
                                        try {
                                            byte[] imageBytes = Base64.decode(imageUrl, Base64.DEFAULT);
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                                            ivPetImage.setImageBitmap(bitmap);
                                        } catch (Exception e) {
                                            ivPetImage.setImageResource(R.drawable.dog);
                                        }
                                    }
                                } else {
                                    ivPetImage.setImageResource(R.drawable.dog);
                                }
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            ivPetImage.setImageResource(R.drawable.dog);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error){
                        ivPetImage.setImageResource(R.drawable.dog);
                    }
                });

                // Load admin contact from Admins node
                DatabaseReference adminRef = FirebaseDatabase.getInstance()
                        .getReference("Admins")
                        .child(adminId);

                adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot adminSnapshot) {
                        if (adminSnapshot.exists()) {
                            String adminName = adminSnapshot.child("name").getValue(String.class);
                            String adminPhone = adminSnapshot.child("phone").getValue(String.class);
                            tvAdminContact.setText("Contact " + adminName + " for pickup: " + adminPhone);
                        } else {
                            tvAdminContact.setText("Contact admin for pickup: 123-456-7890");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        tvAdminContact.setText("Failed to load admin contact.");
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                tvAccepted.setText("Failed to load data.");
            }
        });
    }
}
