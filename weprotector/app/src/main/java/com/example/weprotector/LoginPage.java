package com.example.weprotector;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    EditText emailid, password;
    TextView loginBtn, signupBtn;
    ProgressBar progressbar;

    FirebaseAuth authProfile;
    private static final String TAG = "LoginPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        emailid = findViewById(R.id.emailid);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);
        progressbar = findViewById(R.id.progressbar);

        authProfile = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = emailid.getText().toString().trim();
                String textPassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(textEmail)) {
                    showError(emailid, "Please Enter Your Email", "Email is required");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    showError(emailid, "Please Re-enter Your Email", "Valid Email is Required");
                } else if (TextUtils.isEmpty(textPassword)) {
                    showError(password, "Please Enter Your Password", "Password is Required");
                } else {
                    progressbar.setVisibility(View.VISIBLE);
                    loginUser(textEmail, textPassword);
                }
            }
        });

        // Signup button
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, SignupPage.class);
                startActivity(intent);
            }
        });
    }

    private void showError(EditText field, String toastMsg, String errorMsg) {
        Toast.makeText(LoginPage.this, toastMsg, Toast.LENGTH_SHORT).show();
        field.setError(errorMsg);
        field.requestFocus();
    }

    private void loginUser(String email, String pwd) {
        authProfile.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = authProfile.getCurrentUser();

                            if (firebaseUser != null && firebaseUser.isEmailVerified()) {
                                Intent intent = new Intent(LoginPage.this, HomePage.class);
                                startActivity(intent);
                                finish();
                            } else if (firebaseUser != null) {
                                firebaseUser.sendEmailVerification();
                                authProfile.signOut();
                                showAlertDialog();
                            }
                        } else {
                            handleLoginError(task);
                        }

                        progressbar.setVisibility(View.GONE);
                    }
                });
    }

    private void handleLoginError(Task<AuthResult> task) {
        try {
            throw task.getException();
        } catch (FirebaseAuthInvalidUserException e) {
            showError(emailid, "Account not found", "User does not exist. Please register.");
        } catch (FirebaseAuthInvalidCredentialsException e) {
            showError(emailid, "Login failed", "Invalid email or password.");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(LoginPage.this, "Login error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginPage.this);
        builder.setTitle("Email Not Verified")
                .setMessage("Please verify your email before logging in.")
                .setPositiveButton("Open Email App", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = authProfile.getCurrentUser();
        if (user != null) {
            // If the user is already logged in, redirect to HomePage
            if (user.isEmailVerified()) {
                startActivity(new Intent(LoginPage.this, HomePage.class));
                finish();
            } else {
                // If email is not verified, prompt for verification
                user.sendEmailVerification();
                authProfile.signOut();
                showAlertDialog();
            }
        } else {
            Toast.makeText(LoginPage.this, "You can Login Now", Toast.LENGTH_SHORT).show();
        }
    }
}
