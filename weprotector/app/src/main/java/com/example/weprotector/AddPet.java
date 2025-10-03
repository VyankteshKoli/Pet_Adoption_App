package com.example.weprotector;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPet extends AppCompatActivity {

    private EditText etPetName, etPetBreed, etPetAge, etPetVaccination, etPetSterilization, etPetSize, etPetDescription;
    private Button btnSavePet;
    private CircleImageView profileimage;

    private DatabaseReference databaseReference;
    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pet);

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Pets");

        etPetName = findViewById(R.id.etPetName);
        etPetBreed = findViewById(R.id.etPetBreed);
        etPetAge = findViewById(R.id.etPetAge);
        etPetVaccination = findViewById(R.id.etPetVaccination);
        etPetSterilization = findViewById(R.id.etPetSterilization);
        etPetSize = findViewById(R.id.etPetSize);
        etPetDescription = findViewById(R.id.etPetDescription);
        btnSavePet = findViewById(R.id.btnSavePet);
        profileimage = findViewById(R.id.profileimage);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Saving Pet");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        profileimage.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Pet Image"), PICK_IMAGE_REQUEST);
        });

        btnSavePet.setOnClickListener(v -> savePet());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            profileimage.setImageURI(imageUri);
        }
    }

    private void savePet() {
        
        String name = etPetName.getText().toString().trim();
        String breed = etPetBreed.getText().toString().trim();
        String ageStr = etPetAge.getText().toString().trim();
        String vaccination = etPetVaccination.getText().toString().trim();
        String sterilization = etPetSterilization.getText().toString().trim();
        String size = etPetSize.getText().toString().trim();
        String description = etPetDescription.getText().toString().trim();

        if (name.isEmpty() || breed.isEmpty() || ageStr.isEmpty() || vaccination.isEmpty() ||
                sterilization.isEmpty() || size.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri == null) {
            Toast.makeText(this, "Please select a pet image", Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter a valid age", Toast.LENGTH_SHORT).show();
            return;
        }

        String petId = databaseReference.push().getKey();
        if (petId == null) {
            Toast.makeText(this, "Error generating Pet ID", Toast.LENGTH_SHORT).show();
            return;
        }
        String imageBase64 = encodeImageToBase64(imageUri);
        if (imageBase64 == null) {
            Toast.makeText(this, "Failed to encode image", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        AddPetAdmin pet = new AddPetAdmin(
                petId, name, breed, age, vaccination, sterilization, size, description, imageBase64
        );

        databaseReference.child(petId).setValue(pet)
                .addOnSuccessListener(aVoid -> {
                    progressDialog.dismiss();

                    Toast.makeText(AddPet.this, "Pet added successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddPet.this, AdminHomepage.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(AddPet.this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private String encodeImageToBase64(Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
