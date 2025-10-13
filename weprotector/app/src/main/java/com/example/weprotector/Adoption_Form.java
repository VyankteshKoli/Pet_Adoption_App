package com.example.weprotector;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Adoption_Form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adoption_form);

        TextView submitButton = findViewById(R.id.submit);

        submitButton.setOnClickListener(view -> {

            int score = 0;

            RadioGroup petExperienceGroup = findViewById(R.id.petExperienceGroup);
            RadioGroup yardGroup = findViewById(R.id.yardGroup);
            RadioGroup workGroup = findViewById(R.id.workGroup);
            EditText aloneHours = findViewById(R.id.aloneHours);
            RadioGroup moneyGroup = findViewById(R.id.moneyGroup);
            RadioGroup familyComfortGroup = findViewById(R.id.familyComfortGroup);
            RadioGroup otherPetsGroup = findViewById(R.id.otherPetsGroup);
            RadioGroup longTermGroup = findViewById(R.id.longTermGroup);

            if (petExperienceGroup.getCheckedRadioButtonId() == -1 ||
                    yardGroup.getCheckedRadioButtonId() == -1 ||
                    moneyGroup.getCheckedRadioButtonId() == -1 ||
                    familyComfortGroup.getCheckedRadioButtonId() == -1 ||
                    longTermGroup.getCheckedRadioButtonId() == -1 ||
                    aloneHours.getText().toString().trim().isEmpty()) {

                Toast.makeText(Adoption_Form.this, "❗ Please answer all required questions.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (petExperienceGroup.getCheckedRadioButtonId() == R.id.experienceYes) score++;
            if (yardGroup.getCheckedRadioButtonId() == R.id.yardYes) score++;
            if (moneyGroup.getCheckedRadioButtonId() == R.id.moneyYes) score++;
            if (familyComfortGroup.getCheckedRadioButtonId() == R.id.familyYes) score++;
            if (longTermGroup.getCheckedRadioButtonId() == R.id.longTermYes) score++;

            boolean aloneValid = true;
            try {
                int hours = Integer.parseInt(aloneHours.getText().toString().trim());
                if (hours <= 6) score++;
            } catch (NumberFormatException e) {
                Toast.makeText(Adoption_Form.this, "❌ Please enter a valid number of hours.", Toast.LENGTH_SHORT).show();
                aloneValid = false;
            }

            if (otherPetsGroup.getCheckedRadioButtonId() != -1) score++;

            if (aloneValid) {
                Toast.makeText(Adoption_Form.this, "Score: " + score + "/7", Toast.LENGTH_SHORT).show();

                if (score >= 5) {
                    Toast.makeText(Adoption_Form.this, "✅ You seem ready to take care of a pet!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Adoption_Form.this, "⚠️ You might want to prepare more before adopting.", Toast.LENGTH_LONG).show();
                }
            }


            // Get dog info from previous intent
            String dogName = getIntent().getStringExtra("dogname");
            String dogBreed = getIntent().getStringExtra("dogbreed");
            String dogVaccinated = getIntent().getStringExtra("dogvaccinated");
            String dogAge = getIntent().getStringExtra("dogage");
            String dogSerial = getIntent().getStringExtra("dogserialization");


            // Pass all dog info to Adoption_Fees
            Intent intent = new Intent(Adoption_Form.this, Adoption_Fees.class);
            intent.putExtra("dogname", dogName);
            intent.putExtra("dogbreed", dogBreed);
            intent.putExtra("dogvaccinated", dogVaccinated);
            intent.putExtra("dogage", dogAge);
            intent.putExtra("dogserialization", dogSerial);

            startActivity(intent);
        });

    }
}
