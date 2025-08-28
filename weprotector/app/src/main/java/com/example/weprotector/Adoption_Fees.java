package com.example.weprotector;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Adoption_Fees extends AppCompatActivity {
    TextView infoText, done;

    EditText adopterName, adopterPhone, adopterEmail, adopterAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adoption_fees);

        infoText = findViewById(R.id.infoText);
        done = findViewById(R.id.done);

        adopterName = findViewById(R.id.adopterName);
        adopterPhone = findViewById(R.id.adopterPhone);
        adopterEmail = findViewById(R.id.adopterEmail);
        adopterAddress = findViewById(R.id.adopterAddress);

        String feeExplanation = "1. 🐾 *Adoption Fees Are Common*\n" +
                "Animal shelters, NGOs, or adoption agencies usually charge a fee.\n\n" +
                "This fee covers:\n" +
                "- 🩺 Vaccinations\n" +
                "- 🐶 Neutering/Spaying\n" +
                "- 🚛 Admin/Transport Costs\n\n" +
                "2. 📜 *Legal & Ethical Practice*\n" +
                "- Common in India, US, UK, etc.\n" +
                "- NGOs: Keep receipts/records\n" +
                "- Individuals: Be honest & clear";

        infoText.setText(feeExplanation);

        done.setOnClickListener(view -> {

            // Get adopter details
            String adoptername = adopterName.getText().toString().trim();
            String adopterphone = adopterPhone.getText().toString().trim();
            String adopteremail = adopterEmail.getText().toString().trim();
            String adopteraddress = adopterAddress.getText().toString().trim();

            // Validations
            if (adoptername.isEmpty()) {
                adopterName.setError("Name is required");
                adopterName.requestFocus();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(adopteremail).matches()) {
                adopterEmail.setError("Enter a valid email");
                adopterEmail.requestFocus();
                return;
            }
            if (!adopterphone.matches("\\d{10}")) {
                adopterPhone.setError("Enter a valid 10-digit number");
                adopterPhone.requestFocus();
                return;
            }
            if (adopteraddress.isEmpty()) {
                adopterAddress.setError("Address is required");
                adopterAddress.requestFocus();
                return;
            }

            String dogName = getIntent().getStringExtra("dogname");
            String dogBreed = getIntent().getStringExtra("dogbreed");
            String dogVaccinated = getIntent().getStringExtra("dogvaccinated");
            String dogAge = getIntent().getStringExtra("dogage");
            String dogSerial = getIntent().getStringExtra("dogserialization");


            // Pass all data to Paybill
            Intent intent = new Intent(Adoption_Fees.this, Paybill.class);
            intent.putExtra("name", adoptername);
            intent.putExtra("phone", adopterphone);
            intent.putExtra("email", adopteremail);
            intent.putExtra("address", adopteraddress);

            intent.putExtra("dogname", dogName);
            intent.putExtra("dogbreed", dogBreed);
            intent.putExtra("dogvaccinated", dogVaccinated);
            intent.putExtra("dogage", dogAge);
            intent.putExtra("dogserialization", dogSerial);


            startActivity(intent);

        });

    }
}
