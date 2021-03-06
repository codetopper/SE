package com.example.karat.Staff.SOrder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.karat.Customer.CHome.CHomeDisplay;
import com.example.karat.Customer.COrder.COrderDisplay;
import com.example.karat.Customer.COrder.OrderAdapter;
import com.example.karat.Customer.CProfile.CProfileDisplay;
import com.example.karat.R;
import com.example.karat.Staff.SHome.SHomeDisplay;
import com.example.karat.Staff.SProfile.SProfileDisplay;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.karat.Staff.SOrder.SOrderManager.orders;

public class SOrderDisplay extends AppCompatActivity {
    RecyclerView recyclerView;
    SOrderManager orderManager;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_order_display);
        orderManager = new SOrderManager();
//        Toast.makeText(getApplicationContext(), orders.size()+"", Toast.LENGTH_LONG).show();
        //recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();

        //navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navi);

        bottomNavigationView.setSelectedItemId(R.id.Orders);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        Intent CHomeIntent = new Intent(getApplicationContext(), SHomeDisplay.class);
                        CHomeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(CHomeIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Orders:
                        Intent COrderIntent = new Intent(getApplicationContext(), SOrderDisplay.class);
                        COrderIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(COrderIntent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.Profile:
                        Intent CProfileIntent = new Intent(getApplicationContext(), SProfileDisplay.class);
                        CProfileIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(CProfileIntent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    private void initRecyclerView() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final SOrderAdapter orderAdapter = new SOrderAdapter(orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerView.setAdapter(orderAdapter);
                orderAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
