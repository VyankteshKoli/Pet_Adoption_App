package com.example.weprotector;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

/** @noinspection ALL*/
public class AdminHomepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    TextView adoptPetadmin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpage_homepage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        adoptPetadmin = findViewById(R.id.adoptpetadmin);
        adoptPetadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomepage.this, AddPet.class);
                startActivity(intent);
            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.nav_aboutus) {
//            startActivity(new Intent(this, AboutUs.class));
//        } else
        if (id == R.id.nav_home) {
            startActivity(new Intent(this, AdminHomepage.class));
        } else if (id == R.id.nav_adoptionrequest) {
            startActivity(new Intent(this, AdoptionRequestAdmin.class));
        }
//            else if (id == R.id.nav_howtohelp) {
//            startActivity(new Intent(this, HowtoHelp.class));
//        }
        else if (id == R.id.nav_logout) {
            startActivity(new Intent(this, LogoutPage.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Exit App")
                    .setMessage("Do you want to Exit the app?")
                    .setPositiveButton("Yes", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}