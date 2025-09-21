package com.example.weprotector;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminLoginPage extends AppCompatActivity {

    EditText emailid, password;
    TextView loginBtn, signupBtn;
    ProgressBar progressbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminlogin_page);

        emailid = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.adminloginBtn);
        signupBtn = findViewById(R.id.adminsignupBtn);
        progressbar = findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(v -> {
            String txtEmail = emailid.getText().toString().trim();
            String txtPassword = password.getText().toString().trim();

            if (TextUtils.isEmpty(txtEmail) || !Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                emailid.setError("Valid email is required");
                return;
            }
            if (TextUtils.isEmpty(txtPassword)) {
                password.setError("Password is required");
                return;
            }

            progressbar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(txtEmail, txtPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressbar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                if (firebaseUser != null) {
                                    // Check if this user is really an Admin
                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Admins");
                                    ref.child(firebaseUser.getUid()).get().addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            DataSnapshot snapshot = task1.getResult();
                                            if (snapshot.exists()) {
                                                String role = snapshot.child("role").getValue(String.class);
                                                if ("admin".equals(role)) {
                                                    Toast.makeText(AdminLoginPage.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(AdminLoginPage.this, AdminHomepage.class));
                                                    finish();
                                                } else {
                                                    Toast.makeText(AdminLoginPage.this, "Not an Admin account!", Toast.LENGTH_LONG).show();
                                                    mAuth.signOut();
                                                }
                                            } else {
                                                Toast.makeText(AdminLoginPage.this, "No admin record found!", Toast.LENGTH_LONG).show();
                                                mAuth.signOut();
                                            }
                                        } else {
                                            Toast.makeText(AdminLoginPage.this, "Error: " + task1.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(AdminLoginPage.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        });

        signupBtn.setOnClickListener(v -> {
            startActivity(new Intent(AdminLoginPage.this, AdminSignupPage.class));
        });
    }
}
