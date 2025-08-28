package com.example.weprotector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminSignupPage extends AppCompatActivity {

    EditText name, phoneno, emailid, password, confirmpassword;
    TextView loginBtn, signupBtn;
    ProgressBar progressbar;
    private static final String TAG = "AdminSignupPage";

    private FirebaseAuth mAuth;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminsignup_page);

        name = findViewById(R.id.name);
        phoneno = findViewById(R.id.phoneno);
        emailid = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);
        progressbar = findViewById(R.id.progressbar);

        mAuth = FirebaseAuth.getInstance();

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtName = name.getText().toString().trim();
                String txtPhone = phoneno.getText().toString().trim();
                String txtEmail = emailid.getText().toString().trim();
                String txtPassword = password.getText().toString().trim();
                String txtConfirmPassword = confirmpassword.getText().toString().trim();

                if (TextUtils.isEmpty(txtName)) {
                    name.setError("Name is required");
                    return;
                }
                if (TextUtils.isEmpty(txtPhone)) {
                    phoneno.setError("Phone is required");
                    return;
                }
                if (TextUtils.isEmpty(txtEmail) || !Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
                    emailid.setError("Valid email is required");
                    return;
                }
                if (TextUtils.isEmpty(txtPassword) || txtPassword.length() < 6) {
                    password.setError("Password must be at least 6 characters");
                    return;
                }
                if (!txtPassword.equals(txtConfirmPassword)) {
                    confirmpassword.setError("Passwords do not match");
                    return;
                }

                progressbar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword)
                        .addOnCompleteListener(AdminSignupPage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressbar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();

                                    // Save admin details in Firebase Realtime Database
                                    AdminReadWriteUserDetails adminDetails =
                                            new AdminReadWriteUserDetails(txtName, txtPhone, txtEmail, txtPassword, "admin");

                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admins");
                                    reference.child(firebaseUser.getUid()).setValue(adminDetails)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(AdminSignupPage.this, "Admin registered successfully", Toast.LENGTH_SHORT).show();
                                                        startActivity(new Intent(AdminSignupPage.this, AdminLoginPage.class));
                                                        finish();
                                                    } else {
                                                        Toast.makeText(AdminSignupPage.this, "Failed to save admin: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        password.setError("Weak password");
                                        password.requestFocus();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        emailid.setError("Invalid email");
                                        emailid.requestFocus();
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        emailid.setError("User already exists");
                                        emailid.requestFocus();
                                    } catch (Exception e) {
                                        Log.e(TAG, e.getMessage());
                                        Toast.makeText(AdminSignupPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
            }
        });

        loginBtn.setOnClickListener(v -> {
            startActivity(new Intent(AdminSignupPage.this, AdminLoginPage.class));
        });
    }
}
