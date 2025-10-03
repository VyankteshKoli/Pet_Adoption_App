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
    TextView dognameis, dogbreedis, dogvacinatedis, dogageis, dogserialization;
    CheckBox agree_condition;
    Button submit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paybill);

        agree_condition = findViewById(R.id.agree_condition);
        submit = findViewById(R.id.submit);

        dognameis = findViewById(R.id.nameis);
        dogbreedis = findViewById(R.id.breedis);
        dogvacinatedis = findViewById(R.id.vaccinatedis);
        dogageis = findViewById(R.id.ageis);
        dogserialization = findViewById(R.id.serializationis);

        nameTextView = findViewById(R.id.name);
        emailTextView = findViewById(R.id.email);
        phoneTextView = findViewById(R.id.phone);
        addressTextView = findViewById(R.id.address);

      
        String dogName = getIntent().getStringExtra("dogname");
        String dogBreed = getIntent().getStringExtra("dogbreed");
        String dogVaccinated = getIntent().getStringExtra("dogvaccinated");
        String dogAge = getIntent().getStringExtra("dogage");
        String dogSerial = getIntent().getStringExtra("dogserialization");

        
        String adminId = getIntent().getStringExtra("adminId");

        
        dognameis.setText("Dog Name: " + (dogName != null ? dogName : "N/A"));
        dogbreedis.setText("Breed: " + (dogBreed != null ? dogBreed : "N/A"));
        dogvacinatedis.setText("Vaccinated: " + (dogVaccinated != null ? dogVaccinated : "N/A"));
        dogageis.setText("Age: " + (dogAge != null ? dogAge : "N/A"));
        dogserialization.setText("Serialization: " + (dogSerial != null ? dogSerial : "N/A"));
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

                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("adoptionRequests");
                    String requestId = dbRef.push().getKey();

                    
                    Map<String, Object> adopterInfo = new HashMap<>();
                    adopterInfo.put("name", name);
                    adopterInfo.put("email", email);
                    adopterInfo.put("phone", phone);
                    adopterInfo.put("address", address);

                    
                    Map<String, Object> petInfo = new HashMap<>();
                    petInfo.put("dogName", dogName);
                    petInfo.put("breed", dogBreed);
                    petInfo.put("vaccinated", dogVaccinated);
                    petInfo.put("age", dogAge);
                    petInfo.put("serialization", dogSerial);

                
                    Map<String, Object> requestData = new HashMap<>();
                    requestData.put("adopterInfo", adopterInfo);
                    requestData.put("petInfo", petInfo);
                    requestData.put("status", "pending");
                    requestData.put("adminId", adminId);  
                    requestData.put("timestamp", System.currentTimeMillis());

                    if (requestId != null) {
                        dbRef.child(requestId).setValue(requestData)
                                .addOnSuccessListener(aVoid -> {
                                    new AlertDialog.Builder(Paybill.this)
                                            .setTitle("Thank You!")
                                            .setMessage("Your Request Submitted Successfully.\nAdmin Will Review Soon..")
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
                    Toast.makeText(Paybill.this, "Please agree Terms & Condition", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
