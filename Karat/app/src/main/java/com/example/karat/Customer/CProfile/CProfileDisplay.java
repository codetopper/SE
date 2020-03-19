package com.example.karat.Customer.CProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.karat.Customer.Cart.CartDisplay;
import com.example.karat.Login.LoginDisplay;
import com.example.karat.Login.ResetPwDisplay;
import com.example.karat.R;
import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CProfileDisplay extends AppCompatActivity {

    private Button logOffBtn, changePwBtn, editProfileBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_profile_display);

        initialiseUI();

        //Log off Button
        logOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logOffIntent = new Intent(getApplicationContext(), LoginDisplay.class);
                startActivity(logOffIntent);
                overridePendingTransition(0,0);
            }
        });

        //ChangePw Button
        changePwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePwIntent = new Intent(getApplicationContext(), ResetPwDisplay.class);
                startActivity(changePwIntent);
                overridePendingTransition(0,0);
            }
        });

        //EditProfile Button
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfileIntent = new Intent(getApplicationContext(), CEditProfileDisplay.class);
                startActivity(editProfileIntent);
                overridePendingTransition(0,0);
            }
        });

        //Navigation code
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.setSelectedItemId(R.id.Profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), CHomeDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Orders:
                        startActivity(new Intent(getApplicationContext(), COrderDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(), CProfileDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    private void initialiseUI(){
        logOffBtn = findViewById(R.id.logOff);
        changePwBtn = findViewById(R.id.changePw);
        editProfileBtn = findViewById(R.id.editProfile);
    }

    public void goToCart(View v) {
        Intent i = new Intent(getBaseContext(), CartDisplay.class);
        startActivity(i);
        overridePendingTransition(0,0);
    }

}
