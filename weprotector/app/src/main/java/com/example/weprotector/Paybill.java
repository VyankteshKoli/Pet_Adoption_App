package com.example.weprotector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Paybill extends AppCompatActivity {

    TextView infoText, nameTextView, emailTextView, phoneTextView, addressTextView;
    TextView dognameis, dogbreedis;
    CheckBox agree_condition;
    Button submit;

    // Firebase reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference petsRef = database.getReference("pets");
    DatabaseReference adoptersRef = database.getReference("adopters");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paybill);

        agree_condition = findViewById(R.id.agree_condition);
        submit = findViewById(R.id.submit);

        dognameis = findViewById(R.id.nameis);
        dogbreedis = findViewById(R.id.breedis);

        nameTextView = findViewById(R.id.name);
        emailTextView = findViewById(R.id.email);
        phoneTextView = findViewById(R.id.phone);
        addressTextView = findViewById(R.id.address);

        // Get Pet Info from Intent
        String dogName = getIntent().getStringExtra("dogname");
        String dogBreed = getIntent().getStringExtra("dogbreed");

        dognameis.setText("Dog Name: " + dogName);
        dogbreedis.setText("Breed: " + dogBreed);

        // Get Adopter Info from Intent
        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");
        String address = getIntent().getStringExtra("address");

        nameTextView.setText("Adopter Name: " + name);
        emailTextView.setText("Email ID: " + email);
        phoneTextView.setText("Contact No: " + phone);
        addressTextView.setText("Address: " + address);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (agree_condition.isChecked()) {

                    // Get the reference to Firebase
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("adoptions");
                    String adoptionId = dbRef.push().getKey();

                    // Extract the data from the intent
                    String name = getIntent().getStringExtra("name");
                    String email = getIntent().getStringExtra("email");
                    String phone = getIntent().getStringExtra("phone");
                    String address = getIntent().getStringExtra("address");
                    String dogName = getIntent().getStringExtra("dogname");
                    String dogBreed = getIntent().getStringExtra("dogbreed");

                    // Create adopter info map
                    Map<String, Object> adopterInfo = new HashMap<>();
                    adopterInfo.put("name", name);
                    adopterInfo.put("email", email);
                    adopterInfo.put("phone", phone);
                    adopterInfo.put("address", address);

                    // Create pet info map
                    Map<String, Object> petInfo = new HashMap<>();
                    petInfo.put("dogName", dogName);
                    petInfo.put("breed", dogBreed);
                    petInfo.put("age", "2 years");

                    // Combine adopter and pet info into one map
                    Map<String, Object> adoptionData = new HashMap<>();
                    adoptionData.put("adopterInfo", adopterInfo);
                    adoptionData.put("petInfo", petInfo);

                    if (adoptionId != null) {
                        dbRef.child(adoptionId).setValue(adoptionData)
                                .addOnSuccessListener(aVoid -> {

                                    new AlertDialog.Builder(Paybill.this)
                                            .setTitle("Thank You!")
                                            .setMessage("Your's Form Submitted Successfully.\nOur Team Will Reach You Out Soon..")
                                            .setPositiveButton("OK", (dialog, which) -> {

                                                Intent intent = new Intent(Paybill.this, HomePage.class);
                                                startActivity(intent);
                                                finish();
                                            })
                                            .setCancelable(false)
                                            .show();
                                })
                                .addOnFailureListener(e -> {

                                    Toast.makeText(Paybill.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }

                } else {

                    Toast.makeText(Paybill.this, "Please agree Terms &amp; Condition", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
