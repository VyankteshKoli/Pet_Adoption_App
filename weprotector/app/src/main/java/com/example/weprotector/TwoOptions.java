package com.example.weprotector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TwoOptions extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twooptions);

        TextView l1,l2;

        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TwoOptions.this,LoginPage.class);
                startActivity(intent);
            }
        });


        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TwoOptions.this,AdminLoginPage.class);
                startActivity(intent);
            }
        });

    }
}