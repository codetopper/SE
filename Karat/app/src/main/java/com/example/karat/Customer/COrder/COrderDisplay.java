package com.example.karat.Customer.COrder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.karat.Customer.Cart.CartDisplay;
import com.example.karat.R;
import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class COrderDisplay extends AppCompatActivity {
    RecyclerView recyclerView;
    COrderManager orderManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_order_display);


        //recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        orderManager = new COrderManager(1);
        initRecyclerView();

        //navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.setSelectedItemId(R.id.Orders);

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

    private void initRecyclerView() {
        OrderAdapter orderAdapter = new OrderAdapter(orderManager.cOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);
    }

    public void goToCart(View v) {
        Intent i = new Intent(getBaseContext(), CartDisplay.class);
        startActivity(i);
        overridePendingTransition(0,0);
    }
}
