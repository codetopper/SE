package com.example.karat.Staff.SHome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.example.karat.R;
import com.example.karat.Staff.SOrder.SOrderDisplay;
import com.example.karat.Staff.SProfile.SProfileDisplay;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.karat.Customer.COrder.CustomerOrders.purchase;

public class SHomeDisplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_home_display);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.setSelectedItemId(R.id.Home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), SHomeDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Orders:
                        initData();
                        startActivity(new Intent(getApplicationContext(), SOrderDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Profile:
                        startActivity(new Intent(getApplicationContext(), SProfileDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void initData() {
        purchase(1, "Apple", 1, 1, 0.5, "1/3/2020", 1, "Giant");
        purchase(2, "Orange", 1, 2, 1.0, "1/3/2020", 1, "Giant");
        purchase(3, "Pear", 1, 1, 0.8, "3/3/2020", 1, "Giant");
        purchase(4, "Pineapple", 1, 2, 7.8, "4/3/2020", 1, "Giant");
        purchase(5, "Durian", 2, 4, 50, "10/3/2020", 1, "Giant");
    }
}
