package com.example.karat.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.karat.R;
import com.example.karat.order.COrderDisplay;
import com.example.karat.profile.CProfileDisplay;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.karat.order.CustomerOrders.purchase;

public class CHomeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_home_display);


        //cart

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.setSelectedItemId(R.id.Home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), CHomeDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Orders:
                        initData();
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

    private void initData() {
        purchase(1, "Apple", 1);
        purchase(2, "Orange", 1);
        purchase(3, "Pear", 1);
        purchase(4, "Pineapple", 1);
        purchase(5, "Durian", 2);
    }
}
