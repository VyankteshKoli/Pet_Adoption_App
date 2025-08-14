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

public class SignupPage extends AppCompatActivity {

    EditText name, phoneno, emailid, password, confirmpassword;
    TextView loginBtn, signupBtn;
    ProgressBar progressbar;
    private static final String TAG = "SignupPage";

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        name = findViewById(R.id.name);
        phoneno = findViewById(R.id.phoneno);
        emailid = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);
        progressbar = findViewById(R.id.progressbar);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textName = name.getText().toString().trim();
                String textPhone = phoneno.getText().toString().trim();
                String textEmail = emailid.getText().toString().trim();
                String textPassword = password.getText().toString().trim();
                String textConfirmPassword = confirmpassword.getText().toString().trim();

                Pattern mobilePattern = Pattern.compile("[6-9][0-9]{9}");
                Matcher mobileMatcher = mobilePattern.matcher(textPhone);

                if (TextUtils.isEmpty(textName)) {
                    name.setError("Full Name is required");
                    name.requestFocus();
                } else if (TextUtils.isEmpty(textPhone)) {
                    phoneno.setError("Mobile No. is required");
                    phoneno.requestFocus();
                } else if (textPhone.length() != 10 || !mobileMatcher.matches()) {
                    phoneno.setError("Enter a valid 10-digit mobile number");
                    phoneno.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    emailid.setError("Email is required");
                    emailid.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    emailid.setError("Enter a valid email");
                    emailid.requestFocus();
                } else if (TextUtils.isEmpty(textPassword)) {
                    password.setError("Password is required");
                    password.requestFocus();
                } else if (textPassword.length() < 6) {
                    password.setError("Password must be at least 6 characters");
                    password.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPassword)) {
                    confirmpassword.setError("Please confirm your password");
                    confirmpassword.requestFocus();
                } else if (!textPassword.equals(textConfirmPassword)) {
                    confirmpassword.setError("Passwords do not match");
                    confirmpassword.requestFocus();
                } else {
                    progressbar.setVisibility(View.VISIBLE);
                    registerUser(textName, textPhone, textEmail, textPassword);
                }
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupPage.this, LoginPage.class));
            }
        });
    }

    private void registerUser(String name, String phone, String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressbar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            ReadWriteUserDetails userDetails = new ReadWriteUserDetails(name, phone, email, password);

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Registered Users");
                            ref.child(user.getUid()).setValue(userDetails)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> dbTask) {
                                            if (dbTask.isSuccessful()) {
                                                user.sendEmailVerification()
                                                        .addOnCompleteListener(emailTask -> {
                                                            if (emailTask.isSuccessful()) {
                                                                Toast.makeText(SignupPage.this, "Registration successful. Please verify your email.", Toast.LENGTH_LONG).show();
                                                            } else {
                                                                Toast.makeText(SignupPage.this, "Email verification failed.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            } else {
                                                Toast.makeText(SignupPage.this, "Failed to store user data.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                SignupPage.this.password.setError("Weak password. Use letters, numbers, and symbols.");
                                SignupPage.this.password.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                SignupPage.this.emailid.setError("Invalid or already-used email.");
                                SignupPage.this.emailid.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                SignupPage.this.emailid.setError("Email already registered.");
                                SignupPage.this.emailid.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage(), e);
                                Toast.makeText(SignupPage.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
