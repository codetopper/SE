package com.example.karat.Staff.SOrder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.karat.Customer.COrder.OrderAdapter;
import com.example.karat.R;
import com.example.karat.Staff.SHome.SHomeDisplay;
import com.example.karat.Staff.SProfile.SProfileDisplay;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SOrderDisplay extends AppCompatActivity {
    RecyclerView recyclerView;
    SOrderManager orderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_order_display);

        //recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        orderManager = new SOrderManager(1);
        initRecyclerView();

        //navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.setSelectedItemId(R.id.Orders);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        startActivity(new Intent(getApplicationContext(), SHomeDisplay.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Orders:
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
    private void initRecyclerView() {
        OrderAdapter orderAdapter = new OrderAdapter(orderManager.sOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
    }
}
