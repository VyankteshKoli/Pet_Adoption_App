package com.example.weprotector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HowtoHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howtohelp);  

        Button helpButton = findViewById(R.id.helpButton);

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Toast.makeText(HowtoHelp.this, "Thank you for your interest!", Toast.LENGTH_SHORT).show();

                // OR navigate to another activity (uncomment if you have another Activity)
                // Intent intent = new Intent(HowtoHelp.this, AnotherActivity.class);
                // startActivity(intent);
            }
        });
    }
}
