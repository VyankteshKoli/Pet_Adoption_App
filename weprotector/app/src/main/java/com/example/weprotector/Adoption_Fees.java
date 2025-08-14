package com.example.weprotector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Adoption_Fees extends AppCompatActivity {
    TextView infoText,done;

    EditText adopterName,adopterPhone,adopterEmail,adopterAddress;

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
                    "- 🐶 Neutering/Spaying\n"+
                    "- 🚛 Admin/Transport Costs\n\n" +
                    "2. 📜 *Legal & Ethical Practice*\n" +
                    "- Common in India, US, UK, etc.\n" +
                    "- NGOs: Keep receipts/records\n" +
                    "- Individuals: Be honest & clear";

            infoText.setText(feeExplanation);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String adoptername = adopterName.getText().toString().trim();
                String adopterphone = adopterPhone.getText().toString().trim();
                String adopteremail = adopterEmail.getText().toString().trim();
                String adopteraddress = adopterAddress.getText().toString().trim();

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

                // Pass both adopter's details and dog info to PayBill
                Intent intent = new Intent(Adoption_Fees.this, Paybill.class);
                intent.putExtra("name", adoptername);
                intent.putExtra("phone", adopterphone);
                intent.putExtra("email", adopteremail);
                intent.putExtra("address", adopteraddress);

                // Pass dog details to PayBill
                intent.putExtra("dogname", dogName);
                intent.putExtra("dogbreed", dogBreed);

                startActivity(intent);
            }

        });


//            gotItButton.setOnClickListener(v -> finish()); // closes the activity
        }
    }
